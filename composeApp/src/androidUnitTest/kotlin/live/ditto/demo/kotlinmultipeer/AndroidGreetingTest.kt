package live.ditto.demo.kotlinmultipeer

import DittoManager
import Greeting
import org.junit.Assert.assertTrue
import org.junit.Test

object MockDitto : DittoManager() {
    override val version = "0.0.0"

    override fun startSync() { /* no-op */ }
}

class AndroidGreetingTest {
    @Test
    fun testExample() {
        assertTrue("Check Android is mentioned", Greeting(MockDitto).greet().contains("Android"))
    }
}
