package br.edu.ifsp.scl.sc3033953.pingpongscoreboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


//----------------------------------------
//ETAPA 2: ViewModel + mutableStateOf
//----------------------------------------
class PingPongViewModelState : ViewModel() {
    var scoreA by mutableIntStateOf(0)
        private set
    var scoreB by mutableIntStateOf(0)
        private set

    fun incrementA() { scoreA++ }
    fun incrementB() { scoreB++ }
    fun reset() { scoreA = 0; scoreB = 0 }
}


//----------------------------------------------
//ETAPA3: ViewModel + stateFlow
//---------------------------------------------
class PingPongViewModelFlow : ViewModel(){
    private val _uiState = MutableStateFlow(MatchState())
    val uiState = _uiState.asStateFlow()

    fun incrementA(){
        _uiState.value = _uiState.value.copy(scoreA = _uiState.value.scoreA + 1)
    }
    fun incrementB(){
        _uiState.value = _uiState.value.copy(scoreB = _uiState.value.scoreB + 1)
    }
    fun reset(){
        _uiState.value = MatchState(0,0)
    }

}


//----------------------------------------------
//ETAPA4: ViewModel + SavedStateHandle
//---------------------------------------------

class PingPongViewModelSavedState(private val savedStateHandle: SavedStateHandle) : ViewModel(){
    val scoraA = savedStateHandle.getStateFlow("scoreA", 0)
    val scoraB = savedStateHandle.getStateFlow("scoreB", 0)

    fun incrementA(){savedStateHandle["scoreA"] = scoraA.value + 1}
    fun incrementB(){savedStateHandle["scoreB"] = scoraB.value + 1}
    fun reset(){
        savedStateHandle["scoreA"] = 0
        savedStateHandle["scoreB"] = 0
    }
}