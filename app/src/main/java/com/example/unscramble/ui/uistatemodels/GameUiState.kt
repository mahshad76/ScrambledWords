package com.example.unscramble.ui.uistatemodels

data class GameUiState(
    val currentScrambledWord: String = "",
    val isGuessCorrect: Boolean = true,
    val score: Int = 0
)
