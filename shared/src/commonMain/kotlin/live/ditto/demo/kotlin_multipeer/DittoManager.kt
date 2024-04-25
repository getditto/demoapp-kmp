package live.ditto.demo.kotlin_multipeer

interface DittoManager {
    val version: String
}

expect fun getDittoManager(): DittoManager
