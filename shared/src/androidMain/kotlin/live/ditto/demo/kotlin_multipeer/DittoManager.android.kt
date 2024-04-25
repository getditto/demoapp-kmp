package live.ditto.demo.kotlin_multipeer

import live.ditto.Ditto
import live.ditto.android.DefaultAndroidDittoDependencies
import org.koin.java.KoinJavaComponent.getKoin

class AndroidDittoManager : DittoManager {
    private val dependencies = DefaultAndroidDittoDependencies(getKoin().get())
    val ditto: Ditto = Ditto(dependencies)

    override val version: String = ditto.sdkVersion
}

actual fun getDittoManager(): DittoManager = AndroidDittoManager()
