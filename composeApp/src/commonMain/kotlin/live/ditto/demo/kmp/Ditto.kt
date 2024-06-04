package live.ditto.demo.kmp

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect open class Ditto() {
    open val version: String
    open val presence: DittoPresence

    open fun startSync()

    open fun stopSync()
}