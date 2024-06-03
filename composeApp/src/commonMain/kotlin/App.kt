import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ditto_kmp.composeapp.generated.resources.Res
import com.composegears.tiamat.Navigation
import com.composegears.tiamat.navController
import com.composegears.tiamat.navDestination
import com.composegears.tiamat.rememberNavController
import ditto_kmp.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    val ditto = remember { DittoManager() }
    val platform = remember { getPlatform() }

    val versionInfoScreen by navDestination<Unit> {
        MaterialTheme {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Center,
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Running on ${platform.name}")
                Text("Ditto ${ditto.version}")
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

        MaterialTheme {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                }

                Button(onClick = { ditto.startSync() }) {
                    Text("Start Sync")
                }

                PresenceView(
                    ditto = ditto,
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
