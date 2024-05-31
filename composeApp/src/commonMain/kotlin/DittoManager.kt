@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect open class DittoManager() {
    val version: String

    fun startSync()
}
