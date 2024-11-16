package com.example

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Cart : AppCompatActivity() {

    private lateinit var cartItemsLayout: LinearLayout
    private lateinit var checkoutButton: Button
    private lateinit var totalPriceTextView: TextView
    private lateinit var cartRecyclerView: RecyclerView
    private var totalPrice: Double = 0.0 // Store the total price of items in cart.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartItemsLayout = findViewById(R.id.cartItemsLayout)
        checkoutButton = findViewById(R.id.checkoutButton)
        totalPriceTextView = findViewById(R.id.totalPrice)
        cartRecyclerView = findViewById(R.id.cartRecyclerView)

        // Set up RecyclerView
        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.setHasFixedSize(true)

        val cartItems = intent.getParcelableArrayListExtra<Menu.FoodItem>("cartItems")?.toMutableList() ?: mutableListOf()

// Display initial cart items
        displayCartItems(cartItems)

        // Set up the adapter
        val cartAdapter = CartAdapter(cartItems) { updatedTotalPrice ->
            totalPrice = updatedTotalPrice
            totalPriceTextView.text = "Total: R$totalPrice"
        }
        cartRecyclerView.adapter = cartAdapter

        // Set up click listener for the checkout button
        checkoutButton.setOnClickListener {
            if (cartItems.isEmpty()) {
                Toast.makeText(
                    this,
                    "Your cart is empty. Please add items to the cart.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this, Payment::class.java)
                intent.putParcelableArrayListExtra("cartItems", ArrayList(cartItems))
                startActivity(intent)
            }
        }


    }

    private fun displayCartItems(cartItems: List<Menu.FoodItem>) {
        if (cartItems.isEmpty()) {
            totalPriceTextView.text = "Your cart is empty."
            cartRecyclerView.visibility = View.GONE // Hide the RecyclerView if empty
            checkoutButton.isEnabled = false // Disable checkout button
        } else {
            totalPrice = cartItems.sumOf { it.price * it.quantity }
            totalPriceTextView.text = "Total: R$totalPrice"
            cartRecyclerView.visibility = View.VISIBLE // Show the RecyclerView
            checkoutButton.isEnabled = true // Enable checkout button
        }
    }


    class CartAdapter(
        private val cartItems: MutableList<Menu.FoodItem>,
        private val onPriceUpdated: (Double) -> Unit
    ) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
            return CartViewHolder(view)
        }

        override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
            val item = cartItems[position]
            holder.foodName.text = item.name
            holder.foodPrice.text = "R${item.price * item.quantity}"
            holder.foodImage.setImageResource(item.imageResId)
            holder.foodQuantity.text = item.quantity.toString()

            holder.increaseButton.setOnClickListener {
                item.quantity++
                notifyItemChanged(position)
                updateTotalPrice()
            }

            holder.decreaseButton.setOnClickListener {
                if (item.quantity > 1) {
                    item.quantity--
                    notifyItemChanged(position)
                } else {
                    cartItems.removeAt(position)
                    notifyItemRemoved(position)
                }
                updateTotalPrice()
            }

            holder.removeButton.setOnClickListener {
                cartItems.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, cartItems.size)
                updateTotalPrice()
            }
        }

        override fun getItemCount(): Int = cartItems.size

        private fun updateTotalPrice() {
            val totalPrice = cartItems.sumOf { it.price * it.quantity }
            onPriceUpdated(totalPrice)
        }

        inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val foodImage: ImageView = itemView.findViewById(R.id.foodImage)
            val foodName: TextView = itemView.findViewById(R.id.foodName)
            val foodPrice: TextView = itemView.findViewById(R.id.foodPrice)
            val foodQuantity: TextView = itemView.findViewById(R.id.foodQuantity)
            val increaseButton: Button = itemView.findViewById(R.id.increaseButton)
            val decreaseButton: Button = itemView.findViewById(R.id.decreaseButton)
            val removeButton: Button = itemView.findViewById(R.id.removeButton)
        }
    }

}
