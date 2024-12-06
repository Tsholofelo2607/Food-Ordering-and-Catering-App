
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

class Payment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Dummy Order Items
        val orderItems = listOf(
            OrderItem(R.drawable.fish, "Spring Rolls", 2, 50.0),
            OrderItem(R.drawable.meals, "Grilled Chicken", 1, 120.0)
        )

        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewOrderSummary)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = OrderAdapter(orderItems)

        // Calculate Total Price
        val total = orderItems.sumOf { it.price * it.quantity }
        findViewById<TextView>(R.id.totalAmount).text = "Total: R $total"

        // Payment Button Listeners
        findViewById<Button>(R.id.btnCreditCard).setOnClickListener {
            // Navigate to Credit/Debit Card payment form
        }

        findViewById<Button>(R.id.btnCashOnDelivery).setOnClickListener {
            // Handle Cash on Delivery option
        }
    }

    class OrderAdapter(private val orderItems: List<OrderItem>) :
        RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

        inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val itemImage: ImageView = view.findViewById(R.id.itemImage)
            val itemName: TextView = view.findViewById(R.id.itemName)
            val itemQuantity: TextView = view.findViewById(R.id.itemQuantity)
            val itemPrice: TextView = view.findViewById(R.id.itemPrice)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_order_summary, parent, false)
            return OrderViewHolder(view)
        }

        override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
            val orderItem = orderItems[position]
            holder.itemImage.setImageResource(orderItem.image)
            holder.itemName.text = orderItem.name
            holder.itemQuantity.text = "Quantity: ${orderItem.quantity}"
            holder.itemPrice.text = "Price: R ${orderItem.price}"
        }

        override fun getItemCount(): Int = orderItems.size
    }

    data class OrderItem(
        val image: Int,
        val name: String,
        val quantity: Int,
        val price: Double
    )
}


