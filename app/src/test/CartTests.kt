import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Assert.*
class CartTests {
    private lateinit var cartService: CartService
    private lateinit var mockFirestore: FirebaseFirestore

    @Before
    fun setup() {
        // Initialize your mock FirebaseFirestore here
        mockFirestore = mock(FirebaseFirestore::class.java)
        cartService = CartService(mockFirestore) // Your CartService that interacts with Firestore
    }

    @Test
    fun testAddItemToCart() {
        val item = CartItem(name = "Pap", price = 10.0, quantity = 2)
        cartService.addItemToCart(item)

        val cart = cartService.getCart() // Assume this retrieves the current cart
        assertTrue(cart.contains(item))
    }

    @Test
    fun testRemoveItemFromCart() {
        val item = CartItem(name = "Pap", price = 10.0, quantity = 2)
        cartService.addItemToCart(item)
        cartService.removeItemFromCart(item)

        val cart = cartService.getCart()
        assertFalse(cart.contains(item))
    }

    @Test
    fun testCartTotalCalculation() {
        val item1 = CartItem(name = "Pap", price = 10.0, quantity = 2)
        val item2 = CartItem(name = "Wedges", price = 20.0, quantity = 1)

        cartService.addItemToCart(item1)
        cartService.addItemToCart(item2)

        val total = cartService.calculateTotal()
        assertEquals(40.0, total, 0.01)
    }

    @Test
    fun testEmptyCart() {
        cartService.clearCart()
        val cart = cartService.getCart()
        assertTrue(cart.isEmpty())
    }
}