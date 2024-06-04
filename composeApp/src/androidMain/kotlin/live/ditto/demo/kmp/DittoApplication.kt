package live.ditto.demo.kmp

import android.app.Application
import initKoin
import org.koin.android.ext.koin.androidContext

class DittoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@DittoApplication)
        }
    }
}
