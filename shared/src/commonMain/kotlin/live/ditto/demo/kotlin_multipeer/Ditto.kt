package live.ditto.demo.kotlin_multipeer

interface Ditto {
    val name: String
}

expect fun getDitto(): Ditto
