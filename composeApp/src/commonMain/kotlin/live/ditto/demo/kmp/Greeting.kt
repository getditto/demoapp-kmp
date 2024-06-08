package live.ditto.demo.kmp

class Greeting(
    private val dittoManager: Ditto,
) {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Running on ${platform.name} \nlive.ditto.demo.kmp.Ditto ${dittoManager.version}"
    }
}
