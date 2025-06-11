package org.example.czechtoeuroconvertionestimator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import org.example.czechtoeuroconvertionestimator.data.ConvertorClient
import org.example.czechtoeuroconvertionestimator.data.createHttpClient
import org.example.czechtoeuroconvertionestimator.data.local.createDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(
                client = remember {
                    ConvertorClient(
                        createHttpClient(engine = io.ktor.client.engine.okhttp.OkHttp.create())
                    )
                },
                prefs = remember {
                    createDataStore(applicationContext)
                }
            )
        }
    }
}