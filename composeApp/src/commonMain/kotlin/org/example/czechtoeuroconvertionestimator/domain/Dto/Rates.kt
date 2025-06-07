package org.example.czechtoeuroconvertionestimator.domain.Dto

import kotlinx.serialization.Serializable

@Serializable
data class Rates(
    val CZK: Int,
    val EUR: Double,
)