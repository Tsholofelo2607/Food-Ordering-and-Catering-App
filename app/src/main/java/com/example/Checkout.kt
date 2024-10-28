package com.example

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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


            // Retrieve the cart items from the intent
            val cartItems: MutableList<Menu.FoodItem> =
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
            findViewById<Button>(R.id.checkoutButton).setOnClickListener {
                checkout() // Handle checkout logic
            }

            // Back to Menu button
            findViewById<Button>(R.id.checkoutButton).setOnClickListener {
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
                finish() // Optional: Close the Checkout activity
            }
        }


        private fun calculateTotalPrice(cartItems: List<Menu.FoodItem>) {
        totalPrice = cartItems.sumOf { it.price }
        totalPriceTextView.text = "Total: R${String.format("%.2f", totalPrice)}"
    }

    private fun removeFromCart(foodItem: Menu.FoodItem) {
        // Update total price after removal
        totalPrice -= foodItem.price
        totalPriceTextView.text = "Total: R${String.format("%.2f", totalPrice)}"

    }

    private fun checkout() {
        // Implement your checkout logic here
        Toast.makeText(this, "Proceed Checkout: R${String.format("%.2f", totalPrice)}", Toast.LENGTH_SHORT).show()
        ShowInfo()


    }

    class CartAdapter(
        private val cartItems: MutableList<Menu.FoodItem>,
        private val onRemoveFromCart: (Menu.FoodItem) -> Unit
    ) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

        inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val foodName: TextView = itemView.findViewById(R.id.cartFoodName)
            val foodPrice: TextView = itemView.findViewById(R.id.cartFoodPrice)
            val removeButton: Button = itemView.findViewById(R.id.removeButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_item_view, parent, false)
            return CartViewHolder(view)
        }

        override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
            val foodItem = cartItems[position]
            holder.foodName.text = foodItem.name
            holder.foodPrice.text = "R${foodItem.price}"

            holder.removeButton.setOnClickListener {
                onRemoveFromCart(foodItem)
                cartItems.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        override fun getItemCount(): Int = cartItems.size
    }

    private fun ShowInfo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to proceed?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            // Action for "Yes" button
            Toast.makeText(this, "Order Received", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            // Action for "No" button
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()

    }
}