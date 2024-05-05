package live.ditto.demo.kotlinmultipeer

import Greeting
import org.junit.Assert.assertTrue
import org.junit.Test

class Test {
    @Test
    fun testExample() {
        assertTrue("Check Android is mentioned", Greeting().greet().contains("Android"))
    }
}
