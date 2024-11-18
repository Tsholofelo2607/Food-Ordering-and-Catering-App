import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.junit.Assert.*

class MenuTests {

    private lateinit var menuService: MenuService

    @Before
    fun setup() {
        menuService = MenuService() // Your service that interacts with Firebase or local storage
    }

    @Test
    fun testSearchFoodItem() {
        val result = menuService.searchFood("Pizza")
        assertNotNull(result)
        assertTrue(result.isNotEmpty())

        val noResult = menuService.searchFood("NonExistentItem")
        assertTrue(noResult.isEmpty())
    }

    @Test
    fun testFilterFoodByCategory() {
        val category = "Vegetarian"
        val filteredItems = menuService.filterFoodByCategory(category)
        assertTrue(filteredItems.all { it.category == category })

        val allItems = menuService.filterFoodByCategory("All")
        assertFalse(allItems.isEmpty())
    }

    @Test
    fun testUIFoodItemDisplay() {
        val items = menuService.getMenuItems()
        assertTrue(items.all { it.name.isNotEmpty() && it.price > 0.0 })
        // Test for layout size adjustment based on screen size
    }
}
