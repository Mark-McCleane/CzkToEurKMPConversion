package org.example.czechtoeuroconvertionestimator.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ConversionScreen(
    modifier: Modifier = Modifier,
    valueToBeConverted: String,
    onValueChange: (String) -> Unit,
    supportingText: @Composable () -> Unit,
    valueToBeConvertedTrailingIcon: @Composable () -> Unit,
    showResult : Boolean,
    resultTextFieldValue: String,
    resultTextFieldTrailingIcon: @Composable () -> Unit
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = valueToBeConverted,
            onValueChange = onValueChange,
            placeholder = { Text("Please Enter A Value") },
            singleLine = true,
            supportingText = supportingText,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = valueToBeConvertedTrailingIcon,
            modifier = modifier.fillMaxWidth()
        )

        AnimatedVisibility(showResult) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                TextField(
                    value = resultTextFieldValue,
                    onValueChange = {},
                    singleLine = true,
                    readOnly = true,
                    trailingIcon = resultTextFieldTrailingIcon,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}