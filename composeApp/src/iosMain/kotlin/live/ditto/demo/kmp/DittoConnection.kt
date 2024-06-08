package live.ditto.demo.kmp

import cocoapods.DittoObjC.DITConnection
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.Serializable

@Serializable
data class DittoConnection(
    val id: String,
    val peer1: ByteArray,
    val peer2: ByteArray,
    val connectionType: DittoConnectionType,
)

@OptIn(ExperimentalForeignApi::class)
fun DITConnection.asModel(): DittoConnection {
    return DittoConnection(
        id = id,
        peer1 = peer1.toByteArray(),
        peer2 = peer2.toByteArray(),
        connectionType = type.asModel(),
    )
}