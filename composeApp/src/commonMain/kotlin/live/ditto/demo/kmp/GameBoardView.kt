package live.ditto.demo.kmp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import live.ditto.demo.kmp.GameViewModel.GameState

@Composable
fun GameBoardView(
    modifier: Modifier = Modifier.fillMaxWidth(),
    ditto: Ditto,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: GameViewModel = GameViewModel(ditto, coroutineScope),
) {
    val gameState: StateFlow<GameState> = viewModel.gameState

    val customButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        disabledBackgroundColor = Color.LightGray,
        disabledContentColor = Color.Black,
    )

    LaunchedEffect(key1 = gameState) {
        launch {
            gameState.collect { json ->
            }
        }
    }

    Column {
        Row {
            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = customButtonColors,
            ) {
                Text("1")
            }
            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = customButtonColors,
            ) {
                Text("2")
            }
            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = customButtonColors,
            ) {
                Text("3")
            }
        }
        Row {
            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = customButtonColors,
            ) {
                Text("4")
            }
            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = customButtonColors,
            ) {
                Text("5")
            }
            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = customButtonColors,
            ) {
                Text("6")
            }
        }
        Row {
            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = customButtonColors,
            ) {
                Text("7")
            }
            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = customButtonColors,
            ) {
                Text("8")
            }
            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = customButtonColors,
            ) {
                Text("9")
            }
        }
    }
}