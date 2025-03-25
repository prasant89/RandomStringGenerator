package com.p.randomstringgenerator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.p.randomstringgenerator.data.model.RandomTextData
import com.p.randomstringgenerator.data.repository.GenerateRandomStringRepository
import com.p.randomstringgenerator.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomStringViewModel @Inject constructor(
    private val repository: GenerateRandomStringRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        collectRandomTextData()
    }

    private fun collectRandomTextData() {
        viewModelScope.launch {
            repository.getAllStrings().collectLatest { randomTextList ->
                _uiState.value = _uiState.value.copy(generatedStrings = randomTextList)

            }
        }
    }

    fun generateString(length: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                repository.fetchAndInsertString(length.toInt())
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }

    }

    fun deleteAllStrings() {
        viewModelScope.launch {
            repository.deleteAllStrings()
        }
    }

    fun deleteString(randomTextData: RandomTextData) {
        viewModelScope.launch {
            try {
                if (randomTextData.id != null) {
                    repository.deleteStringById(randomTextData.id)
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}



