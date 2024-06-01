import DittoConnectionType.AccessPoint
import DittoConnectionType.Bluetooth
import DittoConnectionType.P2PWiFi
import DittoConnectionType.WebSocket
import cocoapods.DittoObjC.DITConnectionType
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.Serializable

@Serializable
enum class DittoConnectionType {
    Bluetooth,
    AccessPoint,
    P2PWiFi,
    WebSocket,
}

@OptIn(ExperimentalForeignApi::class)
fun DITConnectionType.asModel(): DittoConnectionType =
    when (this) {
        0UL -> Bluetooth
        1UL -> AccessPoint
        2UL -> P2PWiFi
        3UL -> WebSocket
        else -> WebSocket
    }
