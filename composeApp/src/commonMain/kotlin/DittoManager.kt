interface DittoManager {
    val version: String
}

expect fun getDittoManager(): DittoManager
