import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import live.ditto.Presence

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DittoPresence(private val presence: Presence) {
    actual fun currentGraph(): String = presence.graph.json()

    actual fun observeAsFlow(): Flow<String> =
        callbackFlow {
            val dittoPresenceObserver =
                presence.observe { graph ->
                    trySend(graph.json())
                }
            awaitClose {
                dittoPresenceObserver.close()
            }
        }
}
