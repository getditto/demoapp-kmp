package live.ditto.demo.kotlin_multipeer


class IOSDittoManager : DittoManager {
    override val version: String = "iOS DittoManager"
}

actual fun getDittoManager(): DittoManager = IOSDittoManager()
