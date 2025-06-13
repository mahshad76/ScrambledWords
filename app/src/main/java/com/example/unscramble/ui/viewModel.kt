package com.example.unscramble.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import com.example.unscramble.ui.uistatemodels.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameViewModel : ViewModel() {
    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()
    private var uiState = MutableStateFlow(GameUiState())
    val uiStateFlow: StateFlow<GameUiState>
        get() = uiState.asStateFlow()

    ///why cant i use remember
    var guessState = mutableStateOf("")

    init {
        resetGame()
    }

    fun produceRandomWord(): String {
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return produceRandomWord()
        } else {
            usedWords.add(currentWord)

            val tempWord = currentWord.toCharArray()
            tempWord.shuffle()
            return tempWord.joinToString("")
        }
    }

    fun resetGame() {
        usedWords.clear()
        uiState.value = GameUiState(currentScrambledWord = produceRandomWord())
    }

    fun checkUserGuess() {
        if (guessState.value == currentWord) {
            uiState.update { uiStateValue -> uiStateValue.copy(score = uiStateValue.score + 1) }

        } else {
            uiState.update { uiStateValue ->
                uiStateValue.copy(isGuessCorrect = false)
            }
        }
    }
}