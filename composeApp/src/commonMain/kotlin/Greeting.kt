class Greeting(
    private val dittoManager: Ditto,
) {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Running on ${platform.name} \nDitto ${dittoManager.version}"
    }
}
