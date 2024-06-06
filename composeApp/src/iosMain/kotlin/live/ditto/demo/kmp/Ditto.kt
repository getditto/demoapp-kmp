package live.ditto.demo.kmp

import cocoapods.DittoObjC.DITDitto
import cocoapods.DittoObjC.DITIdentity
import cocoapods.DittoObjC.DITLogger
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import live.ditto.demo.kmp.GameViewModel.Companion.TAG
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class Ditto actual constructor() {
    init {
        println("Ditto (ios) init")
    }

    private val identity = DITIdentity(offlinePlaygroundWithAppID = Env.DITTO_APP_ID)
    private val ditto =
        DITDitto(identity).also {
            DITLogger.enabled = true
            DITLogger.minimumLogLevel = 3UL // DITLogLevel.Info
            it.setOfflineOnlyLicenseToken(Env.DITTO_OFFLINE_TOKEN, error = null)
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
        println("Sync started")
    }

    actual open fun stopSync() {
        ditto.stopSync()
        println("Sync stopped")
    }
}