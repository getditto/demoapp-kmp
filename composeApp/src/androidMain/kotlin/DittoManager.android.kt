import AndroidDittoManager.Companion.TAG
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

class AndroidDittoManager : DittoManager {
    companion object {
        const val TAG = "AndroidDittoManager"
        private val dependencies = DefaultAndroidDittoDependencies(getKoin().get())
        private val identity = OfflinePlayground(dependencies, DITTO_APP_ID)
        val ditto: Ditto =
            Ditto(dependencies, identity).also {
                DittoLogger.enabled = true
                DittoLogger.minimumLogLevel = DittoLogLevel.INFO
                it.setOfflineOnlyLicenseToken(DITTO_OFFLINE_TOKEN)
            }
    }

    override val version: String =
        """
        VERSION: ${Ditto.VERSION}
        sdkVersion: ${ditto.sdkVersion}
        """.trimIndent()
}

actual fun getDittoManager(): DittoManager = AndroidDittoManager()

actual fun startSync() {
    val missingPermissions = DittoSyncPermissions(getKoin().get()).missingPermissions()
    Log.d(TAG, "Missing permissions: $missingPermissions.")

    if (missingPermissions.isNotEmpty()) {
        Log.w(TAG, "Missing permissions: $missingPermissions")
    } else {
        with(AndroidDittoManager.ditto) {
            presence.connectionRequestHandler = { connectionRequest ->
                Log.i(TAG, "Connection request from $connectionRequest")
                Allow
            }
            presence.observe {
                for (peer in it.remotePeers) {
                    println("Remote peer: ${peer.deviceName}")
                }
            }
            startSync()
        }
    }
}
