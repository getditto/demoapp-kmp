@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect open class DittoManager() {
    open val version: String
    open val presence: DittoPresence

    open fun startSync()
}
