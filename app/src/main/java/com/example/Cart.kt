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
import com.example.Menu.FoodItem

class Cart : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var totalAmountTextView: TextView
    private lateinit var cartAdapter: CartItemAdapter
    private var cartItems = mutableListOf<FoodItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        totalAmountTextView = findViewById(R.id.totalAmountTextView)
        recyclerView = findViewById(R.id.cartRecyclerView)

        // Receive cart items from Menu activity
        cartItems = intent.getParcelableArrayListExtra("cartItems") ?: mutableListOf()

        cartAdapter = CartItemAdapter(cartItems) {
            updateTotal()
        }

        recyclerView.adapter = cartAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        updateTotal()
    }

    private fun updateTotal() {
        val total = cartItems.sumOf { it.price }
        totalAmountTextView.text = "Total: R$total"
    }
    class CartItemAdapter(
        private val cartItems: List<FoodItem>,
        private val onItemUpdated: () -> Unit
    ) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

        inner class CartItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameTextView: TextView = view.findViewById(R.id.cartItemName)
            val priceTextView: TextView = view.findViewById(R.id.cartItemPrice)
            val removeButton: Button = view.findViewById(R.id.removeButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_item, parent, false)
            return CartItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
            val item = cartItems[position]
            holder.nameTextView.text = item.name
            holder.priceTextView.text = "R${item.price}"

            holder.removeButton.setOnClickListener {
                (cartItems as MutableList).removeAt(position)
                notifyItemRemoved(position)
                onItemUpdated()
            }
        }

        override fun getItemCount() = cartItems.size
    }
    class FoodAdapter(
        private val foodItems: List<FoodItem>,
        private val onAddToCartClicked: (FoodItem) -> Unit,
        private val onRemoveFromCartClicked: (FoodItem) -> Unit
    ) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

        inner class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val foodImage: ImageView = view.findViewById(R.id.foodImage)
            val foodName: TextView = view.findViewById(R.id.foodName)
            val foodPrice: TextView = view.findViewById(R.id.foodPrice)
            val addButton: Button = view.findViewById(R.id.addButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item_view, parent, false)
            return FoodViewHolder(view)
        }

        override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
            val foodItem = foodItems[position]
            holder.foodName.text = foodItem.name
            holder.foodPrice.text = "R${foodItem.price}"

            // Set the food image resource
            holder.foodImage.setImageResource(R.drawable.meals) // Placeholder or logic for actual image

            holder.addButton.setOnClickListener {
                onAddToCartClicked(foodItem)
            }
        }

        override fun getItemCount() = foodItems.size
    }
}
