import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Assert.*

class DatabaseTests {

    private lateinit var mockFirestore: FirebaseFirestore
    private lateinit var databaseService: DatabaseService

    @Before
    fun setup() {
        mockFirestore = mock(FirebaseFirestore::class.java)
        databaseService = DatabaseService(mockFirestore)
    }

    @Test
    fun testSaveDataToDatabase() {
        val cartItem = CartItem("Pizza", 10.0, 1)
        val result = databaseService.saveItemToCart(cartItem)
        assertTrue(result)
    }

    @Test
    fun testRetrieveDataFromDatabase() {
        val result = databaseService.getCartItems()
        assertNotNull(result)
    }

    @Test
    fun testErrorHandling() {
        `when`(mockFirestore.collection(anyString())).thenThrow(RuntimeException("Database error"))
        try {
            databaseService.getCartItems()
            fail("Exception not thrown")
        } catch (e: Exception) {
            assertTrue(e is RuntimeException)
        }
    }
}

