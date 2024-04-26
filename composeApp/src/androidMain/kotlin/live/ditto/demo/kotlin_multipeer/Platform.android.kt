package live.ditto.demo.kotlin_multipeer

import android.os.Build.VERSION

class AndroidPlatform : Platform {
    override val name: String = "Android ${VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
