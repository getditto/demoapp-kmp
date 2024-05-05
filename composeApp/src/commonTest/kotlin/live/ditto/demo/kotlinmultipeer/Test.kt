package live.ditto.demo.kotlinmultipeer

import Greeting
import kotlin.test.Test
import kotlin.test.assertTrue

class Test {
    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("Hello"), "Check 'Hello' is mentioned")
    }
}
