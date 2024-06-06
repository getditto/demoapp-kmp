package live.ditto.demo.kmp

import android.util.Log
import live.ditto.Ditto
import live.ditto.DittoConnectionRequestAuthorization.Allow
import live.ditto.DittoIdentity.OfflinePlayground
import live.ditto.DittoLogLevel.INFO
import live.ditto.DittoLogger
import live.ditto.android.DefaultAndroidDittoDependencies
import live.ditto.transports.DittoSyncPermissions
import org.koin.java.KoinJavaComponent

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class Ditto actual constructor() {
    init {
        println("Ditto (android) init")
    }

    private val dependencies = DefaultAndroidDittoDependencies(KoinJavaComponent.getKoin().get())
    private val identity = OfflinePlayground(dependencies, Env.DITTO_APP_ID)
    private val ditto: Ditto =
        Ditto(dependencies, identity).also {
            DittoLogger.enabled = true
            DittoLogger.minimumLogLevel = INFO
            it.setOfflineOnlyLicenseToken(Env.DITTO_OFFLINE_TOKEN)
        }

    // Switch to ditto.sdkVersion in 4.7.2-rc.2
    actual open val version =
        """
        sdkVersion: ${Ditto.VERSION}
        """.trimIndent()

    actual open val presence = DittoPresence(ditto.presence)

    actual open fun startSync() {
        val missingPermissions = DittoSyncPermissions(KoinJavaComponent.getKoin().get()).missingPermissions()
        Log.d(TAG, "Missing permissions: $missingPermissions.")

        if (missingPermissions.isNotEmpty()) {
            Log.w(TAG, "Missing permissions: $missingPermissions")
            return
        }

        with(ditto) {
            presence.connectionRequestHandler = { connectionRequest ->
                Log.i(TAG, "Connection request from $connectionRequest")
                Allow
            }
            startSync()
            Log.i(TAG, "Sync started")
        }
    }

    actual open fun stopSync() {
        ditto.stopSync()
        Log.i(TAG, "Sync stopped")
    }

    companion object {
        const val TAG = "DittoManager"
    }
}