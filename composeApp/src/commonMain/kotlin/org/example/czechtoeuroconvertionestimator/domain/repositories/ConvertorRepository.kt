package org.example.czechtoeuroconvertionestimator.domain.repositories

import io.ktor.client.HttpClient
import org.example.czechtoeuroconvertionestimator.domain.Dto.CurrencyDto
import org.example.czechtoeuroconvertionestimator.util.NetworkError
import org.example.czechtoeuroconvertionestimator.util.Result

interface ConvertorRepository {
    suspend fun convert(): Result<CurrencyDto, NetworkError>
}