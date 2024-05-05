package live.ditto.demo.kotlin_multipeer

import org.junit.Assert.assertTrue
import org.junit.Test

class AndroidGreetingTest {
    @Test
    fun testExample() {
        assertTrue("Check Android is mentioned", Greeting().greet().contains("Android"))
    }
}
