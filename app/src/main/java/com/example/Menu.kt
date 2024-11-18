package com.example

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Menu : AppCompatActivity() {

    // MEALS
    private val foodList = listOf(
        FoodItem("Beef Steak and 1 Side", 100.0, "Meals", R.drawable.beef),
        FoodItem("Wings and 1 Side", 75.0, "Meals", R.drawable.wingschick),
        FoodItem("Wors and 1 Side", 80.0, "Meals", R.drawable.wors),
        FoodItem("Turkey and 1 Side", 80.0, "Meals", R.drawable.meals),
        FoodItem("Fish and 1 Side", 70.0, "Meals", R.drawable.fish),
        FoodItem("Fried Chicken and 1 Side", 70.0, "Meals", R.drawable.meals),

        // TRADITIONAL MEALS
        FoodItem("Ngtha and Pap", 70.0, "Traditional Meals", R.drawable.meals),
        FoodItem("Beef Steak/Chicken hard body", 80.0, "Traditional Meals", R.drawable.meals),
        FoodItem("Menatlana, Malana, Megolwana, Dibete, Dikila, Dipelwana (mix 3)", 50.0, "Traditional Meals", R.drawable.meals),

        // BURGERS
        FoodItem("Beef Burger (patty, cheese, cucumber, lettuce and tomato)", 80.0, "Burgers", R.drawable.meals),
        FoodItem("Chicken Burger (chicken breast, cheese, cucumber, lettuce and tomato)", 70.0, "Burgers", R.drawable.meals),

        // LIGHT MEALS
        FoodItem("Chicken Strips and Salad", 60.0, "Light Meals", R.drawable.meals),

        // PLATTER
        FoodItem("Ribs, Buffalo Wings and Chips", 200.0, "Platter", R.drawable.meals),
        FoodItem("Ribs, Chicken Wings, Wors and Chips", 260.0, "Platter", R.drawable.meals),
        FoodItem("Snack Platter, Raisins, Peanuts and Biscuits", 150.0, "Platter", R.drawable.meals),
        FoodItem("Fruits Platter", 150.0, "Platter", R.drawable.meals),

        // SIDES
        FoodItem("Pap", 10.0, "Sides", R.drawable.meals),
        FoodItem("Chips", 20.0, "Sides", R.drawable.meals),
        FoodItem("Mash Potatoes", 10.0, "Sides", R.drawable.meals),
        FoodItem("Wedges", 20.0, "Sides", R.drawable.meals),
        FoodItem("Roll", 20.0, "Sides", R.drawable.meals),
        FoodItem("Rice", 10.0, "Sides", R.drawable.meals),

        // EXTRA
        FoodItem("Chakalaka", 10.0, "Extra", R.drawable.meals),
        FoodItem("Coslow", 10.0, "Extra", R.drawable.meals),
        FoodItem("Salsa", 10.0, "Extra", R.drawable.meals),

        // FAST FOOD
        FoodItem("Kota Small", 30.0, "Fast Food", R.drawable.meals),
        FoodItem("Kota Medium", 40.0, "Fast Food", R.drawable.meals),
        FoodItem("Kota Large", 45.0, "Fast Food", R.drawable.meals),

        // DRINKS
        FoodItem("Orange Juice", 20.0, "Drinks", R.drawable.meals),
        FoodItem("Still Water", 12.0, "Drinks", R.drawable.meals),
        FoodItem("Sparkling Water", 18.0, "Drinks", R.drawable.meals),
        FoodItem("Wine", 30.0, "Drinks", R.drawable.meals),
        FoodItem("Special Dessert", 45.0, "Drinks", R.drawable.meals),
        FoodItem("Coca Cola 500ml", 19.0, "Drinks", R.drawable.meals)
    )
    private val categoryList = listOf("All") + foodList.map { it.category }.distinct()

    private val cartItems = mutableListOf<FoodItem>()
    private lateinit var foodAdapter: FoodItemAdapter
    private var selectedCategory: String = "All"
    private lateinit var foodItemsLayout: LinearLayout
    private lateinit var foodRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Initialize the button
        val viewCartButton: Button = findViewById(R.id.viewCartButton)

        viewCartButton.setOnClickListener {
            val intent = Intent(this, Cart::class.java)
            intent.putParcelableArrayListExtra("cartItems", ArrayList(cartItems))
            startActivity(intent)
        }


        foodAdapter = FoodItemAdapter(foodList, cartItems)

        val recyclerView: RecyclerView = findViewById(R.id.foodItemsRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = foodAdapter

        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                foodAdapter.filter(newText ?: "", selectedCategory)  // Pass query and selected category
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false  // Don't do anything on query submission
            }
        })


        // Set up Spinner for category selection
        val spinner: Spinner = findViewById(R.id.categorySpinner)
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = categoryAdapter

        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View?, position: Int, id: Long
                ) {
                    selectedCategory = parent.getItemAtPosition(position).toString()
                    // Filter based on the selected category and current search query
                    foodAdapter.filter(findViewById<SearchView>(R.id.searchView).query.toString(), selectedCategory)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Optional: Set default category or do nothing
                }
            }
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.menu.add(0, R.id.nav_menu, 0, "Menu").setIcon(R.drawable.menuicon)
        bottomNavigationView.menu.add(0, R.id.nav_cart_item, 1, "Cart").setIcon(R.drawable.shopping)
        bottomNavigationView.menu.add(0, R.id.nav_bookings, 2, "Bookings").setIcon(R.drawable.book)
        bottomNavigationView.menu.add(0, R.id.nav_settings, 3, "Settings")
            .setIcon(R.drawable.settings)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_menu -> true // Stay on current activity
                R.id.nav_cart_item -> {
                    val intent = Intent(this, Cart::class.java)
                    intent.putParcelableArrayListExtra("cartItems", ArrayList(cartItems))
                    startActivity(intent)
                    true
                }

                R.id.nav_bookings -> {
                    startActivity(Intent(this, Bookings::class.java))
                    true
                }

                R.id.nav_settings -> {
                    startActivity(Intent(this, Settings::class.java))
                    true
                }

                else -> false
            }
        }
    }

    // Adapter to handle food item display and cart interaction
    class FoodItemAdapter(
        private var foodItems: List<FoodItem>,
        private val cartItems: MutableList<FoodItem>) :
        RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder>() {

        // Full list of food items (used for filtering)
        private val foodItemsFullList = foodItems.toList()

        // Holds the filtered list of food items
        var filteredFoodItems = foodItems.toMutableList()

        // ViewHolder class to bind food item data
        inner class FoodItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val foodName: TextView = itemView.findViewById(R.id.foodName)
            val foodPrice: TextView = itemView.findViewById(R.id.foodPrice)
            val foodImage: ImageView = itemView.findViewById(R.id.foodImage)
            val addButton: Button = itemView.findViewById(R.id.addButton)
            val removeButton: Button = itemView.findViewById(R.id.removeButton)

            fun bind(foodItem: FoodItem) {
                foodName.text = foodItem.name
                foodPrice.text = "R${foodItem.price}"
                foodImage.setImageResource(foodItem.imageResId)

                // Show add or remove button based on whether foodItem is in the cart
                if (cartItems.contains(foodItem)) {
                    addButton.visibility = View.GONE
                    removeButton.visibility = View.VISIBLE
                } else {
                    addButton.visibility = View.VISIBLE
                    removeButton.visibility = View.GONE
                }

                // Add button click listener
                addButton.setOnClickListener {
                    cartItems.add(foodItem)
                    notifyDataSetChanged() // Notify to refresh the UI
                }

                // Remove button click listener
                removeButton.setOnClickListener {
                    cartItems.remove(foodItem)
                    notifyDataSetChanged() // Notify to refresh the UI
                }
            }
        }

        // Filter food items by category
        // Filter food items by category and search query
        // Filter food items by category and search query
        fun filter(query: String, category: String) {
            filteredFoodItems.clear()

            // First filter by category
            val filteredByCategory = if (category == "All") {
                foodItemsFullList
            } else {
                foodItemsFullList.filter { it.category.equals(category, ignoreCase = true) }
            }

            // Then filter by the search query (case insensitive)
            filteredFoodItems.addAll(filteredByCategory.filter {
                it.name.contains(query, ignoreCase = true)
            })

            notifyDataSetChanged()  // Notify the adapter that the data has changed
        }




        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item_view, parent, false)
            return FoodItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
            val foodItem = filteredFoodItems[position]
            holder.bind(foodItem) // Bind food item to view holder
        }

        override fun getItemCount(): Int = filteredFoodItems.size
    }

    // FoodItem data class (Parcelable for passing between activities)
    data class FoodItem(
        val name: String,
        val price: Double,
        val category: String,
        val imageResId: Int,
        var quantity: Int = 1 // Add a quantity property with default value 1
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readDouble(),
            parcel.readString() ?: "",
            parcel.readInt()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeDouble(price)
            parcel.writeString(category)
            parcel.writeInt(imageResId)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<FoodItem> {
            override fun createFromParcel(parcel: Parcel): FoodItem = FoodItem(parcel)
            override fun newArray(size: Int): Array<FoodItem?> = arrayOfNulls(size)
        }
    }
}
