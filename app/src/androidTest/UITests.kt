import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITests {

    @Test
    fun testNavigation() {
        onView(withId(R.id.navigation_cart)).perform(click())
        onView(withId(R.id.cart_page)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_menu)).perform(click())
        onView(withId(R.id.menu_page)).check(matches(isDisplayed()))
    }

    @Test
    fun testSettingsPage() {
        onView(withId(R.id.navigation_settings)).perform(click())
        onView(withId(R.id.settings_page)).check(matches(isDisplayed()))
    }

    @Test
    fun testUserFlow() {
        onView(withId(R.id.navigation_menu)).perform(click())
        onView(withId(R.id.menu_page)).perform(selectItem("Pizza"))
        onView(withId(R.id.navigation_cart)).perform(click())
        onView(withId(R.id.cart_page)).perform(placeOrder())
        onView(withId(R.id.confirmation_page)).check(matches(isDisplayed()))
    }

    @Test
    fun testErrorStates() {
        onView(withId(R.id.booking_button)).perform(click()) // Trigger an invalid booking
        onView(withText("Error")).check(matches(isDisplayed()))
    }
}
