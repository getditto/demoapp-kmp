package live.ditto.demo.kmp

import cocoapods.DittoObjC.DITConnectionType
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.Serializable
import live.ditto.demo.kmp.DittoConnectionType.AccessPoint
import live.ditto.demo.kmp.DittoConnectionType.Bluetooth
import live.ditto.demo.kmp.DittoConnectionType.P2PWiFi
import live.ditto.demo.kmp.DittoConnectionType.WebSocket

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
        1UL -> Bluetooth
        2UL -> AccessPoint
        3UL -> P2PWiFi
        4UL -> WebSocket
        else -> WebSocket
    }
