package live.ditto.demo.kmp

import Greeting
import org.junit.Assert
import org.junit.Test

class AndroidGreetingTest {
    @Test
    fun testExample() {
        Assert.assertTrue(
            "Check Android is mentioned",
            Greeting(MockDitto).greet().contains("Android")
        )
    }
}