package live.ditto.demo.kmp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import live.ditto.demo.kmp.GameViewModel.GameColor
import live.ditto.demo.kmp.GameViewModel.GameColor.WHITE

@Composable
fun GameBoardView(
    modifier: Modifier = Modifier.fillMaxWidth(),
    ditto: Ditto,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    vm: GameViewModel = GameViewModel(ditto, coroutineScope),
) {
    // Not used yet
    val gameState: StateFlow<GameState> = vm.gameState

    val myColor = remember { mutableStateOf(vm.myColor) }

    var button1Color = remember { mutableStateOf(WHITE) }
    var button2Color = remember { mutableStateOf(WHITE) }
    var button3Color = remember { mutableStateOf(WHITE) }
    var button4Color = remember { mutableStateOf(WHITE) }
    var button5Color = remember { mutableStateOf(WHITE) }
    var button6Color = remember { mutableStateOf(WHITE) }
    var button7Color = remember { mutableStateOf(WHITE) }
    var button8Color = remember { mutableStateOf(WHITE) }
    var button9Color = remember { mutableStateOf(WHITE) }

    LaunchedEffect(key1 = gameState) {
        launch {
            gameState.collect { game ->
                println("GameBoardView LaunchedEffect fired: $game")
                button1Color.value = game.squares[0]
                button2Color.value = game.squares[1]
                button3Color.value = game.squares[2]
                button4Color.value = game.squares[3]
                button5Color.value = game.squares[4]
                button6Color.value = game.squares[5]
                button7Color.value = game.squares[6]
                button8Color.value = game.squares[7]
                button9Color.value = game.squares[8]
            }
        }
    }

    Column {
        Row {
            Button(
                onClick = { vm.buttonTapped(0) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button1Color.value.color,
                ),
            ) {
                Text("1")
            }
            Button(
                onClick = { vm.buttonTapped(1) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button2Color.value.color,
                ),
            ) {
                Text("2")
            }
            Button(
                onClick = { vm.buttonTapped(2) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button3Color.value.color,
                ),
            ) {
                Text("3")
            }
        }
        Row {
            Button(
                onClick = { vm.buttonTapped(3) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button4Color.value.color,
                ),
            ) {
                Text("4")
            }
            Button(
                onClick = { vm.buttonTapped(4) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button5Color.value.color,
                ),
            ) {
                Text("5")
            }
            Button(
                onClick = { vm.buttonTapped(5) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button6Color.value.color,
                ),
            ) {
                Text("6")
            }
        }
        Row {
            Button(
                onClick = { vm.buttonTapped(6) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button7Color.value.color,
                ),
            ) {
                Text("7")
            }
            Button(
                onClick = { vm.buttonTapped(7) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button8Color.value.color,
                ),
            ) {
                Text("8")
            }
            Button(
                onClick = { vm.buttonTapped(8) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button9Color.value.color,
                ),
            ) {
                Text("9")
            }
        }

        Spacer(Modifier.height(32.dp))

        Row {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { myColor.value = vm.randomColor() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = myColor.value.color
                ),
            ) {
                Text("My Color")
            }
        }
    }
}
