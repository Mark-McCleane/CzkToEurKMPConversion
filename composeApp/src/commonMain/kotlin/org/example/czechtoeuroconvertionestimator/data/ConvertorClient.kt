package org.example.czechtoeuroconvertionestimator.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import org.example.czechtoeuroconvertionestimator.domain.Dto.CurrencyDto
import org.example.czechtoeuroconvertionestimator.util.Constants
import org.example.czechtoeuroconvertionestimator.util.NetworkError
import org.example.czechtoeuroconvertionestimator.util.Result

class ConvertorClient(
    private val httpClient: HttpClient,
) {
    suspend fun getConvertionRate(): Result<CurrencyDto, NetworkError> {
        val result = try {
            httpClient.get(urlString = Constants.URL)
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return when (result.status.value) {
            in 200..299 -> {
                val currencyDto = result.body<CurrencyDto>()
                return Result.Success(currencyDto)
            }

            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}