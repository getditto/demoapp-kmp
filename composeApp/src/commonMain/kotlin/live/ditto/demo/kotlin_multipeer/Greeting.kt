package live.ditto.demo.kotlin_multipeer

class Greeting {
    private val platform: Platform = getPlatform()
    private val dittoManager: DittoManager = getDittoManager()

    fun greet(): String {
        return "Hello, ${platform.name}! \nDitto ${dittoManager.version}"
    }
}
