package com.example

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class Checkout : AppCompatActivity() {
    private lateinit var cartAdapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalPriceTextView: TextView
    private var totalPrice = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Get the button by its ID
        val buttonNavigate = findViewById<Button>(R.id.payment)

        // Set an onClickListener on the button
        buttonNavigate.setOnClickListener {
            // Create an intent to navigate to Payment activity
            val intent = Intent(this, Payment::class.java)
            startActivity(intent)  // Start the Payment activity
        }


        // Retrieve the cart items from the intent
        val cartItems: MutableList<FoodItem> =  // Use FoodItem from com.example.Menu
            intent.getParcelableArrayListExtra("cartItems") ?: mutableListOf()

        recyclerView = findViewById(R.id.cartRecyclerView)
        totalPriceTextView = findViewById(R.id.totalPrice)

        // Initialize the adapter
        cartAdapter = CartAdapter(cartItems) { foodItem ->
            removeFromCart(foodItem)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        // Calculate initial total price
        calculateTotalPrice(cartItems)

        // Checkout button
        findViewById<Button>(R.id.checkout).setOnClickListener {
            checkout() // Handle checkout logic
        }

        // Back to Menu button
        findViewById<Button>(R.id.checkout).setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish() // Optional: Close the Checkout activity
        }

    }

    private fun calculateTotalPrice(cartItems: List<FoodItem>) {  // Use FoodItem from com.example.Menu
        totalPrice = cartItems.sumOf { it.price }
        totalPriceTextView.text = "Total: R${String.format("%.2f", totalPrice)}"
    }


    private fun removeFromCart(foodItem: FoodItem) {  // Use FoodItem from com.example.Menu
        // Update total price after removal
        totalPrice -= foodItem.price
        totalPriceTextView.text = "Total: R${String.format("%.2f", totalPrice)}"
    }


    private fun checkout() {
        // Implement your checkout logic here
        Toast.makeText(this, "Proceed Checkout: R${String.format("%.2f", totalPrice)}", Toast.LENGTH_SHORT).show()
        ShowInfo()
    }

    data class FoodItem(
        val name: String,
        val price: Double,
        val imageResId: Int // Add this property for the image resource ID
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString() ?: "", // Provide a default value if null
            parcel.readDouble(),
            parcel.readInt()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeDouble(price)
            parcel.writeInt(imageResId)
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


    val sampleFoodItem = FoodItem(
        name = "Pizza",
        price = 59.99,
        imageResId = R.drawable.meals // Replace with your actual drawable resource
    )


    class CartAdapter(
        private val items: MutableList<FoodItem>,  // Use FoodItem from com.example.Menu
        private val onRemoveItem: (FoodItem) -> Unit
    ) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

        inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.foodImage)
            val nameTextView: TextView = itemView.findViewById(R.id.foodName)
            val priceTextView: TextView = itemView.findViewById(R.id.foodPrice)
            val removeButton: Button = itemView.findViewById(R.id.removeButton)

            fun bind(foodItem: FoodItem) {
                imageView.setImageResource(foodItem.imageResId)
                nameTextView.text = foodItem.name
                priceTextView.text = "R${foodItem.price}"
                removeButton.setOnClickListener {
                    onRemoveItem(foodItem)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_item, parent, false)
            return CartViewHolder(view)
        }

        override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size
    }

    private fun ShowInfo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to proceed?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            Toast.makeText(this, "Order Received", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}
