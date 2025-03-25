package com.p.randomstringgenerator.presentation.model

import com.p.randomstringgenerator.data.model.RandomTextData

data class UiState(
    val generatedStrings: List<RandomTextData> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
