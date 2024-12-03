package com.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Sample data
        val services = listOf(
            Service(R.drawable.cateringevent, "Event Catering", "Delicious food for all events."),
            Service(R.drawable.cateringfood, "Meal Preps", "Healthy and tasty meal preparation."),
            Service(R.drawable.corporate, "Corporate Packages", "Customized catering for businesses.")
        )

        // Setup RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerServices)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ServiceAdapter(services)
    }
}


class ServiceAdapter(private val services: List<Service>) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        holder.image.setImageResource(service.imageRes)
        holder.title.text = service.title
        holder.description.text = service.description
    }

    override fun getItemCount() = services.size

    class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.serviceImage)
        val title: TextView = itemView.findViewById(R.id.serviceTitle)
        val description: TextView = itemView.findViewById(R.id.serviceDescription)
    }
}
data class Service(
    val imageRes: Int,
    val title: String,
    val description: String
)

