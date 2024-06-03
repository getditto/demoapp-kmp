import Env.DITTO_APP_ID
import Env.DITTO_OFFLINE_TOKEN
import android.util.Log
import live.ditto.Ditto
import live.ditto.DittoConnectionRequestAuthorization.Allow
import live.ditto.DittoIdentity.OfflinePlayground
import live.ditto.DittoLogLevel
import live.ditto.DittoLogger
import live.ditto.android.DefaultAndroidDittoDependencies
import live.ditto.transports.DittoSyncPermissions
import org.koin.java.KoinJavaComponent.getKoin

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class Ditto actual constructor() {
    init {
        println("Ditto (android) init")
    }

    private val dependencies = DefaultAndroidDittoDependencies(getKoin().get())
    private val identity = OfflinePlayground(dependencies, DITTO_APP_ID)
    private val ditto: Ditto =
        Ditto(dependencies, identity).also {
            DittoLogger.enabled = true
            DittoLogger.minimumLogLevel = DittoLogLevel.INFO
            it.setOfflineOnlyLicenseToken(DITTO_OFFLINE_TOKEN)
        }

    // Switch to ditto.sdkVersion in 4.7.2-rc.2
    actual open val version =
        """
        sdkVersion: ${Ditto.VERSION}
        """.trimIndent()

    actual open val presence = DittoPresence(ditto.presence)

    actual open fun startSync() {
        val missingPermissions = DittoSyncPermissions(getKoin().get()).missingPermissions()
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
        }
    }

    companion object {
        const val TAG = "DittoManager"
    }
}
