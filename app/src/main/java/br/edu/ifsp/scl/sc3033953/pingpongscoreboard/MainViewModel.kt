package br.edu.ifsp.scl.sc3033953.pingpongscoreboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


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