import Env.DITTO_APP_ID
import Env.DITTO_OFFLINE_TOKEN
import cocoapods.DittoObjC.DITDitto
import cocoapods.DittoObjC.DITIdentity
import cocoapods.DittoObjC.DITLogger
import cocoapods.DittoObjC.DITPeer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
class IOSDittoManager : DittoManager {
    companion object {
        private val identity = DITIdentity(offlinePlaygroundWithAppID = DITTO_APP_ID)
        val ditto =
            DITDitto(identity).also {
                DITLogger.enabled = true
                DITLogger.minimumLogLevel = 3UL // DITLogLevel.Info
//                DITLogger.minimumLogLevel = 4UL // DITLogLevel.Debug
                it.setOfflineOnlyLicenseToken(DITTO_OFFLINE_TOKEN, error = null)
            }
    }

    override val version =
        """
        sdkVersion: ${ditto.sdkVersion()}
        """.trimIndent()
}

actual fun getDittoManager(): DittoManager = IOSDittoManager()

@OptIn(ExperimentalForeignApi::class)
actual fun startSync() {
    // memScoped { allocPointerTo<ObjCObjectVar<NSError?>>() }
    val errorPtr: CPointer<ObjCObjectVar<NSError?>>? = null

    with(IOSDittoManager.ditto) {
        presence.observe {
            it?.let {
                for (peer in it.remotePeers) {
                    peer as DITPeer
                    println("Remote peer: ${peer.deviceName}")
                }
            }
        }
        startSync(errorPtr)
    }
}
