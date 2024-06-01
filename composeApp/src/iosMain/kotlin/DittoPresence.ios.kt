import cocoapods.DittoObjC.DITPeer
import cocoapods.DittoObjC.DITPresence
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@OptIn(ExperimentalForeignApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DittoPresence(private val presence: DITPresence) {
    actual fun currentGraph(): String = presence.graph.json()

    actual fun observeAsFlow(): Flow<String> =
        callbackFlow {
            val dittoPresenceObserver =
                presence.observe { graph ->
                    @Suppress("NAME_SHADOWING")
                    val graph = graph ?: return@observe

                    for (peer in graph.remotePeers) {
                        peer as DITPeer
                        println("Remote peer: ${peer.asModel().deviceName}")
                    }
                    trySend(graph.json())
                }
            awaitClose {
                dittoPresenceObserver.stop()
            }
        }
}
