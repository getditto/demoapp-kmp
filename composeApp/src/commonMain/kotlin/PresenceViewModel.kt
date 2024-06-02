import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class PresenceViewModel(
    dittoPresence: DittoPresence,
    coroutineScope: CoroutineScope
) {
    private var _graphJson: Flow<String> = dittoPresence.observeAsFlow()

    val graphJson =
        _graphJson.stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            dittoPresence.currentGraph(),
        )
}
