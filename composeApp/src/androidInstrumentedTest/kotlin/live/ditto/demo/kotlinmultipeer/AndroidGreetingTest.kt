package live.ditto.demo.kotlinmultipeer

import Greeting
import org.junit.Test
import kotlin.test.assertTrue

class AndroidGreetingTest {
    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("Android"))
    }
}
