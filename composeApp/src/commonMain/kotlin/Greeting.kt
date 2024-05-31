class Greeting {
    private val platform: Platform = getPlatform()
    private val dittoManager = DittoManager()

    fun greet(): String {
        return "Hello, ${platform.name}! \nDitto ${dittoManager.version}"
    }
}
