package live.ditto.demo.kotlin_multipeer


class IOSDitto : Ditto {
    override val name: String = "iOS Ditto"
}

actual fun getDitto(): Ditto = IOSDitto()
