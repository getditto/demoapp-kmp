package live.ditto.demo.kmp

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composegears.tiamat.Navigation
import com.composegears.tiamat.navController
import com.composegears.tiamat.navDestination
import com.composegears.tiamat.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val vm = remember { MainViewModel() }

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

        Column {
            Button(onClick = { navController.navigate(versionInfoScreen) }) {
                Text("ℹ️")
            }
        }

        val customButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Color.DarkGray,
            contentColor = Color.White,
            disabledBackgroundColor = Color.LightGray,
            disabledContentColor = Color.Black,
        )

        MaterialTheme {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { vm.toggleSync() },
                            modifier = Modifier.weight(1f),
                            colors = customButtonColors,
                        ) {
                            Text("🚦️")
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier.weight(1f),
                            colors = customButtonColors,
                        ) {
                            Text("🟢")
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier.weight(1f),
                            colors = customButtonColors,
                        ) {
                            Text("🟠")
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier.weight(1f),
                            colors = customButtonColors,
                        ) {
                            Text("🔵")
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier.weight(1f),
                            colors = customButtonColors,
                        ) {
                            Text("🟣")
                        }
                    }
                }

                PresenceView(
                    ditto = vm.ditto,
                )
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
