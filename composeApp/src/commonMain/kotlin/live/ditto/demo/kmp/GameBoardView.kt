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
import live.ditto.demo.kmp.GameViewModel.GameColor.WHITE

@Composable
fun GameBoardView(
    modifier: Modifier = Modifier.fillMaxWidth(),
    ditto: Ditto,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    vm: GameViewModel = GameViewModel(ditto, coroutineScope),
) {
    val gameState: StateFlow<GameState> = vm.gameState

    val myColor = remember { mutableStateOf(vm.myColor) }

    val button1Color by vm.button1Color.collectAsState(initial = WHITE)
    val button2Color by vm.button2Color.collectAsState(initial = WHITE)
    val button3Color by vm.button3Color.collectAsState(initial = WHITE)
    val button4Color by vm.button4Color.collectAsState(initial = WHITE)
    val button5Color by vm.button5Color.collectAsState(initial = WHITE)
    val button6Color by vm.button6Color.collectAsState(initial = WHITE)
    val button7Color by vm.button7Color.collectAsState(initial = WHITE)
    val button8Color by vm.button8Color.collectAsState(initial = WHITE)
    val button9Color by vm.button9Color.collectAsState(initial = WHITE)

    LaunchedEffect(key1 = gameState) {
        launch {
            gameState.collect { game ->
            }
        }
    }

    Column {
        Row {
            Button(
                onClick = { vm.buttonTapped(0) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button1Color.color,
                ),
            ) {
                Text("1")
            }
            Button(
                onClick = { vm.buttonTapped(1) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button2Color.color,
                ),
            ) {
                Text("2")
            }
            Button(
                onClick = { vm.buttonTapped(2) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button3Color.color,
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
                    backgroundColor = button4Color.color,
                ),
            ) {
                Text("4")
            }
            Button(
                onClick = { vm.buttonTapped(4) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button5Color.color,
                ),
            ) {
                Text("5")
            }
            Button(
                onClick = { vm.buttonTapped(5) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button6Color.color,
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
                    backgroundColor = button7Color.color,
                ),
            ) {
                Text("7")
            }
            Button(
                onClick = { vm.buttonTapped(7) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button8Color.color,
                ),
            ) {
                Text("8")
            }
            Button(
                onClick = { vm.buttonTapped(8) },
                modifier = Modifier.weight(1f).height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = button9Color.color,
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
