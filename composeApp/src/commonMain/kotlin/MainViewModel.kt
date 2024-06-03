class MainViewModel {
    val ditto = Ditto()

    val version: String
        get() = ditto.version

    private var syncEnabled = false

    fun toggleSync() =
        when (syncEnabled) {
            true -> ditto.stopSync()
            false -> ditto.startSync()
        }
}
