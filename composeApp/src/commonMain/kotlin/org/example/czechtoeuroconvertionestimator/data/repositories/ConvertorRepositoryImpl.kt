package org.example.czechtoeuroconvertionestimator.data.repositories

import io.ktor.client.HttpClient
import org.example.czechtoeuroconvertionestimator.data.ConvertorClient
import org.example.czechtoeuroconvertionestimator.domain.Dto.CurrencyDto
import org.example.czechtoeuroconvertionestimator.domain.repositories.ConvertorRepository
import org.example.czechtoeuroconvertionestimator.util.NetworkError
import org.example.czechtoeuroconvertionestimator.util.Result

class ConvertorRepositoryImpl(private val client: ConvertorClient) : ConvertorRepository {
    override suspend fun convert(): Result<CurrencyDto, NetworkError>  {
        return client.getConvertionRate()
    }
}