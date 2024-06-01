import cocoapods.DittoObjC.DITAddress
import cocoapods.DittoObjC.pubkey
import cocoapods.DittoObjC.siteID
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.serialization.Serializable
import platform.Foundation.NSData
import platform.posix.memcpy

@Serializable
data class DittoAddress(
    val pubkey: ByteArray,
    val siteId: ULong,
)

@OptIn(ExperimentalForeignApi::class)
fun DITAddress.asModel(): DittoAddress {
    return DittoAddress(
        pubkey = pubkey?.toByteArray() ?: byteArrayOf(),
        siteId = siteID,
    )
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    val length = this.length.toInt()
    return ByteArray(length).apply {
        usePinned { memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length) }
    }
}
