package br.edu.ifsp.scl.sc3033953.pingpongscoreboard

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.ifsp.scl.sc3033953.pingpongscoreboard.ui.theme.PingPongScoreBoardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            PingPongScoreBoardTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    //PARA TESTAR, DESCOMENTAR UMA LINHA POR VEZ:
                    //PingPongScreenRemember()
                    //PingPongScreenState()
                    //PingPongScreenFlow()
                    //PingPongScreenSaveState()
                }
            }
        }
    }
}


// ---------------------------------------------
// COMPONENTE VISUAL BASE
// ---------------------------------------------
@Composable
fun PingPongScoreBoard(
    scoreA: Int, scoreB: Int,
    onIncrementA: () -> Unit, onIncrementB: () -> Unit, onReset: () -> Unit,
    title: String
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = title, fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Jogador A", fontSize = 18.sp)
                Text(text = "$scoreA", fontSize = 18.sp)
                Button(onClick = onIncrementA) { Text("+1")}
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Jogador B", fontSize = 18.sp)
                Text(text = "$scoreB", fontSize = 18.sp)
                Button(onClick = onIncrementB) { Text("+1")}
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onReset) { Text("Reiniciar Partida")}

        }
    }

}


// ------------------------------------------------
//TELAS DAS ETAPAS
// ------------------------------------------------

//----------------
//ETAPA 1
//----------------
@Composable
fun PingPongScreenRemember(){
    var scoreA by remember { mutableIntStateOf(0) }
    var scoreB by remember {mutableIntStateOf(0)}
    PingPongScoreBoard(
        scoreA,scoreB,{scoreA ++}, {scoreB++}, {scoreA = 0; scoreB = 0},
        "Etapa 1: Remember"
    )

}


//------------
//ETAPA 2
//------------
@Composable
fun PingPongScreenState(viewModel: PingPongViewModelState = viewModel()) {
    PingPongScoreBoard(
        viewModel.scoreA, viewModel.scoreB,
        viewModel::incrementA, viewModel::incrementB,
        viewModel::reset,
        "Etapa 2: ViewModel + mutableStateOf"
    )
}

//---------------------
//ETAPA 3
//---------------------

@Composable
fun PingPongScreenFlow(viewModel: PingPongViewModelFlow = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    PingPongScoreBoard(
        uiState.scoreA,
        uiState.scoreB,
        viewModel::incrementA,
        viewModel::incrementB,
        viewModel::reset,
        "Etapa 3: ViewModel + StateFlow"
    )
}

//-----------------------
//ETAPA 4
//-----------------------
@Composable
fun PingPongScreenSavedState(viewModel: PingPongViewModelSavedState = viewModel()){
    val scoraA by viewModel.scoraA.collectAsState()
    val scoraB by viewModel.scoraB.collectAsState()

    PingPongScoreBoard(
        scoraA, scoraB,
        viewModel::incrementA,
        viewModel::incrementB,
        viewModel::reset,
        "Etapa 4: ViewModel + SavedStateHandle"
    )
}



