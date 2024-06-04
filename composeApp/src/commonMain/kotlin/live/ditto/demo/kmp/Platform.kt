package live.ditto.demo.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
