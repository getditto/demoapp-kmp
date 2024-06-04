package live.ditto.demo.kmp

import cocoapods.DittoObjC.DITConnection
import cocoapods.DittoObjC.DITPeer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.Serializable

@Serializable
data class Peer(
    val address: DittoAddress,
    val connections: List<DittoConnection>,
    val deviceName: String,
    val dittoSdkVersion: String?,
    val isCompatible: Boolean?,
    val isConnectedToDittoCloud: Boolean,
    val os: String?,
    val peerKey: ByteArray,
    val queryOverlapGroup: Int,
)

@OptIn(ExperimentalForeignApi::class)
fun DITPeer.asModel(): Peer =
    Peer(
        address = address.asModel(),
        connections = connections.map { (it as DITConnection).asModel() },
        deviceName = deviceName,
        dittoSdkVersion = dittoSDKVersion(),
        isCompatible = isCompatible?.boolValue(),
        isConnectedToDittoCloud = isConnectedToDittoCloud(),
        os = os,
        peerKey = peerKey.toByteArray(),
        queryOverlapGroup = queryOverlapGroup.toInt(),
    )
