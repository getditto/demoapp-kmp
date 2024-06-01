import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.multiplatform.webview.util.KLogSeverity
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import saschpe.kase64.base64UrlEncoded

@Composable
fun PresenceView(
    modifier: Modifier = Modifier.fillMaxSize(),
    ditto: DittoManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: PresenceViewModel = PresenceViewModel(ditto.presence, coroutineScope),
) {
    val webViewState =
        rememberWebViewStateWithHTMLFile(
            fileName = "dist/index.html",
        )
    webViewState.webSettings.apply {
        logSeverity = KLogSeverity.Debug
    }
    val navigator = rememberWebViewNavigator()

    val presenceState: StateFlow<String> = viewModel.graphJson

    LaunchedEffect(key1 = presenceState) {
        launch {
            presenceState.collect { json ->
                var base64Json = json.base64UrlEncoded
                base64Json = base64Json.replace("-", "+")
                println("base64Json: $base64Json")
                navigator.evaluateJavaScript("Presence.updateNetwork('$base64Json');")
            }
        }
    }

    PresenceWebView(
        modifier = modifier,
        state = webViewState,
        navigator = navigator,
    )
}

@Composable
fun PresenceWebView(
    modifier: Modifier,
    state: WebViewState,
    navigator: WebViewNavigator,
) {
    WebView(
        modifier = modifier,
        state = state,
        navigator = navigator,
    )
}
