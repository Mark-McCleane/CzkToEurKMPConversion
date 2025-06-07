package org.example.czechtoeuroconvertionestimator

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import org.example.czechtoeuroconvertionestimator.data.ConvertorClient
import org.example.czechtoeuroconvertionestimator.data.createHttpClient

fun MainViewController() = ComposeUIViewController {
    App(
        client = remember {
            ConvertorClient(createHttpClient(Darwin.create()))
        }
    )
}