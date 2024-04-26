package live.ditto.demo.kotlin_multipeer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform