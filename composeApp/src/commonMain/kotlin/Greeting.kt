class Greeting(
    private val dittoManager: DittoManager,
) {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}! \nDitto ${dittoManager.version}"
    }
}
