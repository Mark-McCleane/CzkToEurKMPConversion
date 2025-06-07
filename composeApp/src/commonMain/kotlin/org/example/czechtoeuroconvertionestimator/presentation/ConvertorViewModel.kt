package org.example.czechtoeuroconvertionestimator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.czechtoeuroconvertionestimator.domain.repositories.ConvertorRepository
import org.example.czechtoeuroconvertionestimator.util.onError
import org.example.czechtoeuroconvertionestimator.util.onSuccess

class ConvertorViewModel(
    private val repository: ConvertorRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(0F)
    val state = _state.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun convert(convertValue: Float) {
        viewModelScope.launch {
            repository.convert()
                .onSuccess { convertorDto ->
                    _state.update { convertValue * convertorDto.rates.EUR.toFloat() }
                }
                .onError { networkError ->
                    _error.update {
                        networkError.name
                    }
                }
        }

    }
}