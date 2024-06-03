import Env.DITTO_APP_ID
import Env.DITTO_OFFLINE_TOKEN
import cocoapods.DittoObjC.DITDitto
import cocoapods.DittoObjC.DITIdentity
import cocoapods.DittoObjC.DITLogger
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class Ditto actual constructor() {
    init {
        println("Ditto (ios) init")
    }

    private val identity = DITIdentity(offlinePlaygroundWithAppID = DITTO_APP_ID)
    private val ditto =
        DITDitto(identity).also {
            DITLogger.enabled = true
            DITLogger.minimumLogLevel = 3UL // DITLogLevel.Info
            it.setOfflineOnlyLicenseToken(DITTO_OFFLINE_TOKEN, error = null)
        }

    actual open val version =
        """
        sdkVersion: ${ditto.sdkVersion()}
        """.trimIndent()

    actual open val presence = DittoPresence(ditto.presence)

    @OptIn(ExperimentalForeignApi::class)
    actual open fun startSync() {
        // memScoped { allocPointerTo<ObjCObjectVar<NSError?>>() }
        val errorPtr: CPointer<ObjCObjectVar<NSError?>>? = null

        with(ditto) {
            startSync(errorPtr)
        }
    }
}
