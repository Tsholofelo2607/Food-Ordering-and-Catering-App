class CartViewModelTest {

    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        viewModel = CartViewModel()
    }

    @Test
    fun testAddItemToCart() {
        val foodItem = FoodItem("Pap", 10.0)
        viewModel.addItemToCart(foodItem)

        assertEquals(1, viewModel.getCartSize())
        assertEquals(10.0, viewModel.getTotalPrice(), 0.0)
    }

    @Test
    fun testRemoveItemFromCart() {
        val foodItem = FoodItem("Pap", 10.0)
        viewModel.addItemToCart(foodItem)
        viewModel.removeItemFromCart(foodItem)

        assertEquals(0, viewModel.getCartSize())
        assertEquals(0.0, viewModel.getTotalPrice(), 0.0)
    }

    @Test
    fun testEmptyCart() {
        viewModel.emptyCart()

        assertEquals(0, viewModel.getCartSize())
    }

    @After
    fun tearDown() {
        // Optional: Clean up resources if necessary
    }
}
