package org.example.czechtoeuroconvertionestimator.util

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

actual fun Float.toMoneyValue(): String {
    val formatter = NSNumberFormatter()
    formatter.minimumFractionDigits = 2u
    formatter.maximumFractionDigits = 2u
    return formatter.stringFromNumber(NSNumber(this.toDouble())) ?: "0.00"
}