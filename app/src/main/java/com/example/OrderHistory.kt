package com.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderHistory : AppCompatActivity() {
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)


                // Example order data (you should load actual data from a database or shared preferences)
                val orderList = listOf(
                    Order(
                        orderId = "12345",
                        orderItems = listOf(
                            Menu.FoodItem("Quarter Chicken and 1 Side", 70.0,"Meals", true),
                            Menu.FoodItem("Orange Juice", 20.0, "Drinks",true)
                        ),
                        totalAmount = 18.0,
                        orderDate = "2024-10-07"
                    ),
                    Order(
                        orderId = "54321",
                        orderItems = listOf(
                            Menu.FoodItem("Chakalaka", 10.0, "Extra",true),
                            Menu.FoodItem("Ribs, Buffalo Wings and Chips", 200.0, "Platter",true)
                        ),
                        totalAmount = 25.0,
                        orderDate = "2024-10-06"
                    )
                )

                // Initialize RecyclerView and Adapter
                recyclerView = findViewById(R.id.orderRecyclerView)
                orderAdapter = OrderAdapter(orderList)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = orderAdapter
            }

    data class Order(
        val orderId: String,
        val orderItems: List<Menu.FoodItem>,
        val totalAmount: Double,
        val orderDate: String // Date as string for simplicity; you can use Date if preferred
    )

    class OrderAdapter(
        private val orderList: List<Order>
    ) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

        inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val orderIdTextView: TextView = itemView.findViewById(R.id.orderIdTextView)
            val orderDateTextView: TextView = itemView.findViewById(R.id.orderDateTextView)
            val orderTotalTextView: TextView = itemView.findViewById(R.id.orderTotalTextView)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.order_item_view, parent, false)
            return OrderViewHolder(view)
        }

        override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
            val order = orderList[position]
            holder.orderIdTextView.text = "Order ID: R{order.orderId}"
            holder.orderDateTextView.text = "Date: R{order.orderDate}"
            holder.orderTotalTextView.text = "Total: R${String.format("%.2f", order.totalAmount)}"


        }

        override fun getItemCount(): Int = orderList.size
    }


        }
