package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import com.example.unscramble.ui.uistatemodels.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class viewModel : ViewModel() {
    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()
    private var uiState = MutableStateFlow(GameUiState())
    val uiStateFlow: StateFlow<GameUiState>
        get() = uiState.asStateFlow()

    fun produceRandomWord(): String {
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return produceRandomWord()
        } else {
            usedWords.add(currentWord)

            val tempWord = currentWord.toCharArray()
            return tempWord.shuffle().toString()
        }
    }

    fun resetGame() {
        usedWords.clear()
        uiState.value = GameUiState(currentScrambledWord = produceRandomWord())
    }
}