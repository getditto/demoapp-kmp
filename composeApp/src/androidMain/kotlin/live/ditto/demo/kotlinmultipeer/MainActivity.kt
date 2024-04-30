package live.ditto.demo.kotlinmultipeer

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import live.ditto.transports.DittoSyncPermissions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RequestPermission()
            App()
        }
    }

    @Composable
    @Preview
    fun RequestPermission() {
        val lifecycle = LocalLifecycleOwner.current.lifecycle

        val lifecycleObserver = remember {
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    val missing = DittoSyncPermissions(this).missingPermissions()
                    if (missing.isNotEmpty()) {
                        this.requestPermissions(missing, 0)
                    }
                }
            }
        }
        DisposableEffect(lifecycle, lifecycleObserver) {
            lifecycle.addObserver(lifecycleObserver)
            onDispose {
                lifecycle.removeObserver(lifecycleObserver)
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
