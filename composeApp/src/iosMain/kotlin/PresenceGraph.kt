import cocoapods.DittoObjC.DITPeer
import cocoapods.DittoObjC.DITPresenceGraph
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class PresenceGraph(
    val localPeer: Peer,
    val remotePeers: List<Peer>,
)

@OptIn(ExperimentalForeignApi::class)
fun DITPresenceGraph.json(): String = Json.encodeToString(asModel())

@OptIn(ExperimentalForeignApi::class)
fun DITPresenceGraph.asModel(): PresenceGraph {
    return PresenceGraph(
        localPeer = localPeer.asModel(),
        remotePeers = remotePeers.map { (it as DITPeer).asModel() },
    )
}
