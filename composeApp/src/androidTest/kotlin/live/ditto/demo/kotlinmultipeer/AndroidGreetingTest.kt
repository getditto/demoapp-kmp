package live.ditto.demo.kotlinmultipeer

import Greeting
import junit.framework.Assert.assertTrue
import org.junit.Test

class AndroidGreetingTest {
    @Test
    fun testExample() {
        assertTrue("Check Android is mentioned", Greeting().greet().contains("Android"))
    }
}
