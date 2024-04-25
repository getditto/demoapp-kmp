package live.ditto.demo.kotlin_multipeer

class Greeting {
    private val platform: Platform = getPlatform()
    private val ditto: Ditto = getDitto()

    fun greet(): String {
        return "Hello, ${platform.name}! - ${ditto.name}"
    }
}
