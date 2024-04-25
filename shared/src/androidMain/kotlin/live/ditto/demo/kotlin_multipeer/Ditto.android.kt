package live.ditto.demo.kotlin_multipeer


class AndroidDitto : Ditto {
    override val name: String = "Android Ditto"
}

actual fun getDitto(): Ditto = AndroidDitto()
