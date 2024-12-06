package com.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Cart : AppCompatActivity() {

        private lateinit var cartAdapter: CartAdapter
        private lateinit var totalPriceTextView: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_cart)

            totalPriceTextView = findViewById(R.id.textViewTotalPrice)

            // Dummy Cart Items
            val cartItems = mutableListOf(
                CartItem(R.drawable.fish, "Spring Rolls", 1, 50.0),
                CartItem(R.drawable.meals, "Grilled Chicken", 2, 120.0)
            )

            // Set up RecyclerView
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCart)
            recyclerView.layoutManager = LinearLayoutManager(this)

            cartAdapter = CartAdapter(cartItems) { total ->
                totalPriceTextView.text = "Total: R $total"
            }
            recyclerView.adapter = cartAdapter

            // Initialize total price
            totalPriceTextView.text = "Total: R ${cartItems.sumOf { it.price * it.quantity }}"
        }
    }

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onQuantityChanged: (Double) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemPrice: TextView = view.findViewById(R.id.itemPrice)
        val itemQuantity: TextView = view.findViewById(R.id.itemQuantity)
        val btnIncreaseQuantity: Button = view.findViewById(R.id.btnIncreaseQuantity)
        val btnDecreaseQuantity: Button = view.findViewById(R.id.btnDecreaseQuantity)
        val btnDeleteItem: ImageView = view.findViewById(R.id.btnDeleteItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.itemImage.setImageResource(cartItem.image)
        holder.itemName.text = cartItem.name
        holder.itemPrice.text = "R ${cartItem.price * cartItem.quantity}"
        holder.itemQuantity.text = cartItem.quantity.toString()

        holder.btnIncreaseQuantity.setOnClickListener {
            cartItem.quantity++
            notifyItemChanged(position)
            onQuantityChanged(calculateTotalPrice())
        }

        holder.btnDecreaseQuantity.setOnClickListener {
            if (cartItem.quantity > 1) {
                cartItem.quantity--
                notifyItemChanged(position)
                onQuantityChanged(calculateTotalPrice())
            }
        }

        holder.btnDeleteItem.setOnClickListener {
            cartItems.removeAt(position)
            notifyItemRemoved(position)
            onQuantityChanged(calculateTotalPrice())
        }
    }

    override fun getItemCount(): Int = cartItems.size

    private fun calculateTotalPrice(): Double {
        return cartItems.sumOf { it.price * it.quantity }
    }
}
data class CartItem(
    val image: Int,
    val name: String,
    var quantity: Int,
    val price: Double
)


