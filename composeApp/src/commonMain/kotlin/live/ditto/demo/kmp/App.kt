package live.ditto.demo.kmp

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composegears.tiamat.Navigation
import com.composegears.tiamat.navController
import com.composegears.tiamat.navDestination
import com.composegears.tiamat.rememberNavController
import live.ditto.demo.kmp.GameViewModel.GameColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val vm = MainViewModel()
    val myColor = remember { mutableStateOf(vm.myColor) }

    val versionInfoScreen by navDestination<Unit> {
        val platform = remember { getPlatform() }
        MaterialTheme {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Center,
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Running on ${platform.name}")
                Text("Ditto ${vm.version}")
            }
        }
    }

    val mainScreen by navDestination<Unit> {
        val navController = navController()

        MaterialTheme {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = CenterHorizontally,
            ) {
                PresenceView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    ditto = vm.ditto,
                )

                Spacer(Modifier.height(32.dp))

                GameBoardView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    ditto = vm.ditto,
                )

                Spacer(Modifier.height(32.dp))

                Row {
                    Button(
                        onClick = { navController.navigate(versionInfoScreen) },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = Color.White
                        ),
                    ) {
                        Text("ℹ️")
                    }
                    Button(
                        onClick = { myColor.value = vm.randomColor() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = myColor.value.color
                        ),
                    ) {
                        Text("")
                    }
                    Spacer(
                        modifier = Modifier.weight(1f),
                    )
                    Button(
                        onClick = { vm.toggleSync() },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                        ),
                    ) {
                        Text("Toggle Sync️")
                    }
                }
            }
        }
    }

    val navController = rememberNavController(
        startDestination = mainScreen,
        destinations = arrayOf(
            mainScreen,
            versionInfoScreen,
        )
    )

    Navigation(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
    )
}
