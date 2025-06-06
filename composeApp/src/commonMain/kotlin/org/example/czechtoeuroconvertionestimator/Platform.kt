package org.example.czechtoeuroconvertionestimator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform