package live.ditto.demo.kotlin_multipeer

import cocoapods.DittoObjC.DITDitto
import cocoapods.DittoObjC.DITIdentity
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSUUID

@OptIn(ExperimentalForeignApi::class)
class IOSDittoManager : DittoManager {
    private val identity = DITIdentity(offlinePlaygroundWithAppID = NSUUID.UUID().UUIDString)
    val ditto = DITDitto(identity)

    override val version: String = """
        sdkVersion: ${ditto.sdkVersion()}
    """.trimIndent()
}

actual fun getDittoManager(): DittoManager = IOSDittoManager()
