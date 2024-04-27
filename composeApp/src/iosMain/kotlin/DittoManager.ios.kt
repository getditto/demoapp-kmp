import cocoapods.DittoObjC.DITDitto
import cocoapods.DittoObjC.DITIdentity
import kotlinx.cinterop.*
import platform.Foundation.NSError
import platform.Foundation.NSUUID

@OptIn(ExperimentalForeignApi::class)
class IOSDittoManager : DittoManager {
    companion object {
        private val identity = DITIdentity(offlinePlaygroundWithAppID = NSUUID.UUID().UUIDString)
        val ditto = DITDitto(identity)
    }
    override val version: String = """
        sdkVersion: ${ditto.sdkVersion()}
    """.trimIndent()
}

actual fun getDittoManager(): DittoManager = IOSDittoManager()

@OptIn(ExperimentalForeignApi::class)
actual fun startSync() {
    val errorPtr = memScoped { allocPointerTo<ObjCObjectVar<NSError?>>() }
    IOSDittoManager.ditto.startSync(errorPtr.value)
}
