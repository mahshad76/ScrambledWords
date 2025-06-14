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
    private var _userGuess = MutableStateFlow("")
    val userGuess: StateFlow<String>
        get() = _userGuess.asStateFlow()

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
        if (_userGuess.value == currentWord) {
            uiState.update { uiStateValue ->
                uiStateValue.copy(
                    currentScrambledWord = produceRandomWord(),
                    score = uiStateValue.score + 1,
                    count = uiStateValue.count + 1
                )
            }

        } else {
            uiState.update { uiStateValue ->
                uiStateValue.copy(isGuessCorrect = false)
            }
        }
        _userGuess.update { " " }
    }

    fun updateUserGuess(guess: String) {
        _userGuess.update { guess }
    }

}

