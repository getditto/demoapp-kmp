package live.ditto.demo.kotlin_multipeer.android

import android.app.Application
import live.ditto.demo.kotlin_multipeer.initKoin
import org.koin.android.ext.koin.androidContext

class DittoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@DittoApplication)
        }
    }
}
