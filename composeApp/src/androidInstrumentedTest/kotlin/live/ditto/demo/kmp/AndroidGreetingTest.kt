package live.ditto.demo.kmp

import Greeting
import org.junit.Test
import kotlin.test.assertTrue

class AndroidGreetingTest {
    @Test
    fun testExample() {
        assertTrue(Greeting(MockDitto).greet().contains("Android"))
    }
}