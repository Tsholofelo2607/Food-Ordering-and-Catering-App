import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.junit.Assert.*

class BookingTests {

    private lateinit var bookingService: BookingService

    @Before
    fun setup() {
        bookingService = BookingService() // Your service for bookings
    }

    @Test
    fun testCreateBooking() {
        val validBooking = Booking(date = "2024-12-25", serviceType = "Wedding", guestCount = 50)
        val result = bookingService.createBooking(validBooking)
        assertTrue(result)

        val invalidBooking = Booking(date = "", serviceType = "", guestCount = 0)
        val resultInvalid = bookingService.createBooking(invalidBooking)
        assertFalse(resultInvalid)
    }
}
