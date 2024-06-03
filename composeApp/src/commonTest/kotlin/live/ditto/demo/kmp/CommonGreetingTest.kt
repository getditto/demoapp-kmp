package live.ditto.demo.kmp

import Ditto
import Greeting
import kotlin.test.Test
import kotlin.test.assertTrue

object MockDitto : Ditto() {
    override val version = "0.0.0"

    override fun startSync() { /* no-op */ }
}

class CommonGreetingTest {
    @Test
    fun testExample() {
        assertTrue(Greeting(MockDitto).greet().contains("Hello"), "Check 'Hello' is mentioned")
    }
}
