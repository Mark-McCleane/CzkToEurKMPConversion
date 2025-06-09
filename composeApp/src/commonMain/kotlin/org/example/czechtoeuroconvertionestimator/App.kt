package org.example.czechtoeuroconvertionestimator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import czechtoeuroconvertionestimator.composeapp.generated.resources.Res
import czechtoeuroconvertionestimator.composeapp.generated.resources.allDrawableResources
import czechtoeuroconvertionestimator.composeapp.generated.resources.compose_multiplatform
import io.ktor.client.HttpClient
import org.example.czechtoeuroconvertionestimator.data.ConvertorClient
import org.example.czechtoeuroconvertionestimator.data.repositories.ConvertorRepositoryImpl
import org.example.czechtoeuroconvertionestimator.domain.repositories.ConvertorRepository
import org.example.czechtoeuroconvertionestimator.presentation.ConversionScreen
import org.example.czechtoeuroconvertionestimator.presentation.ConvertorViewModel
import org.example.czechtoeuroconvertionestimator.util.toMoneyValue
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(client: ConvertorClient) {
    MaterialTheme {
        var showResult by remember { mutableStateOf(false) }
        var valueToBeConverted by remember { mutableStateOf("") }
        val repository = ConvertorRepositoryImpl(client)
        val viewModel = ConvertorViewModel(repository)
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getConversionRate()
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Czk To Euro Conversion") }
                )
            }, modifier = Modifier
        ) { innerPadding ->
            Column(
                modifier = Modifier.safeContentPadding().fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConversionScreen(
                    valueToBeConverted = valueToBeConverted,
                    onValueChange = {
                        valueToBeConverted = it
                        if (it.isNotBlank()) {
                            viewModel.convert(it.toFloat())
                            showResult = true
                        } else {
                            showResult = false
                        }
                    },
                    supportingText = { if (state.conversionRate != 0F) Text(text = "Czech Koruna per 1 Euro = ${state.conversionRate} ") },
                    valueToBeConvertedTrailingIcon = {
                        Text(text = "Kč")
                    },
                    showResult = showResult,
                    resultTextFieldValue = state.conversionValue.toMoneyValue(),
                    resultTextFieldTrailingIcon = {
                        Text("€")
                    }
                )
            }
        }
    }
}