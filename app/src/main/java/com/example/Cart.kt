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

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private var cartItems: MutableList<FoodItem> = mutableListOf()
    private lateinit var totalPriceTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.recyclerViewCart)
        totalPriceTextView = findViewById(R.id.totalPrice)

        // Sample data
        cartItems.add(FoodItem("Meals", 10.99, R.drawable.meals)) // replace with actual image resource
        cartItems.add(FoodItem("Ribs PLatter", 8.99, R.drawable.ribsplatter)) // replace with actual image resource

        cartAdapter = CartAdapter(cartItems) { foodItem ->
            removeItem(foodItem)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        updateTotalPrice()
    }

    private fun removeItem(foodItem: FoodItem) {
        cartItems.remove(foodItem)
        cartAdapter.notifyDataSetChanged()
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val total = cartItems.sumOf { it.price }
        totalPriceTextView.text = "Total: R$total"
    }
}

    data class FoodItem(
        val name: String,
        val price: Double,
        val imageResId: Int // Resource ID for the image
    )

    class CartAdapter(
        private val items: MutableList<FoodItem>,
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

