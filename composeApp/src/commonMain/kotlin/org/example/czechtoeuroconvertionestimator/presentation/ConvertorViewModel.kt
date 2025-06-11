package org.example.czechtoeuroconvertionestimator.presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.czechtoeuroconvertionestimator.domain.repositories.ConvertorRepository
import org.example.czechtoeuroconvertionestimator.util.Constants
import org.example.czechtoeuroconvertionestimator.util.onError
import org.example.czechtoeuroconvertionestimator.util.onSuccess

class ConvertorViewModel(
    private val repository: ConvertorRepository,
    private val prefs: DataStore<Preferences>,
) : ViewModel() {

    private val _state = MutableStateFlow(ConvertorState())
    val state = _state.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun convert(convertValue: Float) {
        _state.update {
            it.copy(conversionValue = convertValue * it.conversionRate)
        }
    }

    fun getConversionRate() {
        viewModelScope.launch {
            repository.convert().onSuccess { convertorDto ->
                val conversionRate = convertorDto.rates.EUR.toFloat()
                _state.update { it.copy(conversionRate = conversionRate) }
                prefs.edit {
                    it[floatPreferencesKey(Constants.CONVERSION_RATE)] = conversionRate
                }
            }.onError { networkError ->
                _error.update {
                    networkError.name
                }
                getConversionRateLocally().collect { conversionRate ->
                    _state.update { it.copy(conversionRate = conversionRate) }
                }
            }
        }
    }

    private fun getConversionRateLocally(): Flow<Float> {
        return prefs.data.map {
            val temp = floatPreferencesKey(Constants.CONVERSION_RATE)
            it[temp] ?: 0f
        }
    }
}

data class ConvertorState(val conversionValue: Float = 0F, val conversionRate: Float = 0F)