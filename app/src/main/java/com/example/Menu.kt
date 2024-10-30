
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
import android.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Menu.FoodItem
import com.google.android.material.bottomnavigation.BottomNavigationView


class Menu : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var foodAdapter: FoodAdapter
    private val cartItems = mutableListOf<FoodItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val button: View = findViewById(R.id.login)
        button.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }


        val foodList = listOf(

                // MEALS
            FoodItem("Quarter Chicken and 1 Side", 70.0, "Meals"),
        FoodItem("Beef Steak and 1 Side", 100.0, "Meals"),
        FoodItem("Wings and 1 Side", 75.0, "Meals"),
        FoodItem("Wors and 1 Side", 80.0, "Meals"),
        FoodItem("Turkey and 1 Side", 80.0, "Meals"),
        FoodItem("Fish and 1 Side", 70.0, "Meals"),
        FoodItem("Fried Chicken and 1 Side", 70.0, "Meals"),

        // TRADITIONAL MEALS
        FoodItem("Ngtha and Pap", 70.0, "Traditional Meals"),
        FoodItem("Beef Steak/Chicken hard body", 80.0, "Traditional Meals"),
        FoodItem("Menatlana, Malana, Megolwana, Dibete, Dikila, Dipelwana (mix 3)", 50.0, "Traditional Meals"),

        // BURGERS
        FoodItem("Beef Burger (patty, cheese, cucumber, lettuce and tomato)", 80.0, "Burgers"),
        FoodItem("Chicken Burger (chicken breast, cheese, cucumber, lettuce and tomato)", 70.0, "Burgers"),

        // LIGHT MEALS
        FoodItem("Chicken Strips and Salad", 60.0, "Light Meals"),

        // PLATTER
        FoodItem("Ribs, Buffalo Wings and Chips", 200.0, "Platter"),
        FoodItem("Ribs, Chicken Wings, Wors and Chips", 260.0, "Platter"),
        FoodItem("Snack Platter, Raisins, Peanuts and Biscuits", 150.0, "Platter"),
        FoodItem("Fruits Platter", 150.0, "Platter"),

        // SIDES
        FoodItem("Pap", 10.0, "Sides"),
        FoodItem("Chips", 20.0, "Sides"),
        FoodItem("Mash Potatoes", 10.0, "Sides"),
        FoodItem("Wedges", 20.0, "Sides"),
        FoodItem("Roll", 20.0, "Sides"),
        FoodItem("Rice", 10.0, "Sides"),

        // EXTRA
        FoodItem("Chakalaka", 10.0, "Extra"),
        FoodItem("Coslow", 10.0, "Extra"),
        FoodItem("Salsa", 10.0, "Extra"),

        // FAST FOOD
        FoodItem("Kota Small", 30.0, "Fast Food"),
        FoodItem("Kota Medium", 40.0, "Fast Food"),
        FoodItem("Kota Large", 45.0, "Fast Food"),

        // DRINKS
        FoodItem("Orange Juice", 20.0, "Drinks"),
        FoodItem("Still Water", 12.0, "Drinks"),
        FoodItem("Sparkling Water", 18.0, "Drinks"),
        FoodItem("Wine", 30.0, "Drinks"),
        FoodItem("Special Dessert", 45.0, "Drinks"),
        FoodItem("Coca Cola 500ml", 19.0, "Drinks")
        )


        val categories = listOf(
            "All",
            "Meals",
            "Traditional Meals",
            "Burgers",
            "Light Meals",
            "Platter",
            "Sides",
            "Extra",
            "Fast Food",
            "Drinks"
        )
        val categorySpinner: Spinner = findViewById(R.id.categorySpinner)

        // Set up ArrayAdapter for the Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = categories[position]
                val filteredList = if (selectedCategory == "All") {
                    foodList
                } else {
                    foodList.filter { it.category == selectedCategory }
                }
                foodAdapter.updateList(filteredList)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        recyclerView = findViewById(R.id.recyclerView)
        foodAdapter = FoodAdapter(foodList.toMutableList(),
            onAddToCartClicked = { cartItems.add(it) },
            onRemoveFromCartClicked = { cartItems.remove(it) }
        )
        recyclerView.adapter = foodAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = foodList.filter {
                    it.name.contains(newText ?: "", ignoreCase = true)
                }
                foodAdapter.updateList(filteredList)
                return true
            }
        })

        findViewById<Button>(R.id.viewCartButton).setOnClickListener {
            // Handle showing cart
            showCartItems(cartItems)
        }

            // Find the BottomNavigationView
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

            // Add items to the BottomNavigationView programmatically
            bottomNavigationView.menu.add(0, R.id.nav_menu, 0, "Menu").setIcon(R.drawable.menuicon)
            bottomNavigationView.menu.add(0, R.id.nav_cart_item, 1, "Cart").setIcon(R.drawable.shopping)
            bottomNavigationView.menu.add(0, R.id.nav_bookings, 2, "Bookings").setIcon(R.drawable.book)
            bottomNavigationView.menu.add(0, R.id.nav_settings, 3, "Settings").setIcon(R.drawable.settings)

            // Set up the item selection listener
            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_menu -> {
                        // Redirect to the Home Activity
                        startActivity(Intent(this, Home::class.java))
                        true
                    }
                    R.id.nav_cart_item -> {
                            // Redirect to the Cart Activity
                        startActivity(Intent(this, Cart::class.java))
                        true
                    }
                    R.id.nav_bookings -> {
                        // Redirect to the Order History Activity
                        startActivity(Intent(this, Bookings::class.java))
                        true
                    }
                    R.id.nav_settings -> {
                        // Redirect to the Order History Activity
                        startActivity(Intent(this, Settings::class.java))
                        true
                    }
                    else -> false
                }
            }

    }


    private fun showCartItems(cartItems: List<FoodItem>) {
        val intent = Intent(this, Checkout::class.java)
        intent.putParcelableArrayListExtra("cartItems", ArrayList(cartItems)) // Pass the cart items
        startActivity(intent)
    }


    data class FoodItem(
        val name: String,
        val price: Double,
        val category: String,
        var isInCart: Boolean = false
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readDouble(),
            parcel.readString() ?: "",  // Read the category as a String
            parcel.readByte() != 0.toByte()  // Read isInCart as a Boolean
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeDouble(price)
            parcel.writeString(category)  // Write the category to the Parcel
            parcel.writeByte(if (isInCart) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<FoodItem> {
            override fun createFromParcel(parcel: Parcel): FoodItem {
                return FoodItem(parcel)
            }

            override fun newArray(size: Int): Array<FoodItem?> {
                return arrayOfNulls(size)
            }
        }
    }


    companion object CREATOR : Parcelable.Creator<FoodItem> {
            override fun createFromParcel(parcel: Parcel): FoodItem {
                return FoodItem(parcel)
            }

            override fun newArray(size: Int): Array<FoodItem?> {
                return arrayOfNulls(size)
            }
        }
    }

class FoodAdapter(
    private var foodList: MutableList<FoodItem>, // Changed to MutableList for updating
    private val onAddToCartClicked: (FoodItem) -> Unit,
    private val onRemoveFromCartClicked: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName: TextView = itemView.findViewById(R.id.foodName)
        val foodPrice: TextView = itemView.findViewById(R.id.foodPrice)
        val addButton: Button = itemView.findViewById(R.id.addButton)
        val removeButton: Button = itemView.findViewById(R.id.removeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_item_view, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = foodList[position]
        holder.foodName.text = foodItem.name
        holder.foodPrice.text = "R${foodItem.price}"

        holder.addButton.setOnClickListener {
            foodItem.isInCart = true
            onAddToCartClicked(foodItem)
            notifyItemChanged(position)
        }

        holder.removeButton.setOnClickListener {
            foodItem.isInCart = false
            onRemoveFromCartClicked(foodItem)
            notifyItemChanged(position)
        }

        holder.addButton.visibility = if (foodItem.isInCart) View.GONE else View.VISIBLE
        holder.removeButton.visibility = if (foodItem.isInCart) View.VISIBLE else View.GONE
    }

    override fun getItemCount() = foodList.size

    // Update the adapter’s data and refresh the view
    fun updateList(newFoodList: List<FoodItem>) {
        foodList.clear()
        foodList.addAll(newFoodList) // Update the foodList with new items
        notifyDataSetChanged() // Notify that the data has changed
    }
}
