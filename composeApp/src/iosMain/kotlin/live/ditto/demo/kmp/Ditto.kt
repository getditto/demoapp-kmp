package live.ditto.demo.kmp

import cocoapods.DittoObjC.DITDitto
import cocoapods.DittoObjC.DITDocumentID
import cocoapods.DittoObjC.DITIdentity
import cocoapods.DittoObjC.DITLiveQuery
import cocoapods.DittoObjC.DITLogger
import cocoapods.DittoObjC.DITSubscription
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import live.ditto.demo.kmp.GameViewModel.Companion.TAG
import live.ditto.demo.kmp.GameViewModel.GameState
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

    private var subscription: DITSubscription? = null
    private var observer: DITLiveQuery? = null

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

    private fun startSubscription() {
        subscription = ditto.store
            .collection(COLLECTION_NAME)
            .findByID(DITDocumentID(DOCUMENT_ID))
            .subscribe()
        print("Subscription started")
    }

    private fun stopSubscription() {
        subscription?.cancel()
        subscription = null
        print("Subscription stopped")
    }

    actual open fun startObserver(): Flow<GameState> =
        callbackFlow {
            observer = ditto.store
                .collection(COLLECTION_NAME)
                .findByID(DITDocumentID(DOCUMENT_ID))
                .observeLocal { doc, event ->
                    println("Observer received $event for $doc")
                    doc?.value?.let {
                        @Suppress("UNCHECKED_CAST")
                        val state = GameState.fromMap(it as Map<String, Any?>)
                        println("GameState: $state")
                        trySend(state)
                    }
                }
            print("Observer started")
            awaitClose {}
        }
}
