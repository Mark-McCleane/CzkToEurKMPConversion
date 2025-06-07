package org.example.czechtoeuroconvertionestimator.util

actual fun Float.toMoneyValue(): String = "%.2f".format(this)