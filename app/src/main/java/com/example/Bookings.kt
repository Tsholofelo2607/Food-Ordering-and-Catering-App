
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import java.util.Calendar

class BookingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings)

        // Initialize views
        val eventTypeSpinner: Spinner = findViewById(R.id.eventTypeSpinner)
        val guestsEditText: EditText = findViewById(R.id.guestsEditText)
        val datePickerButton: Button = findViewById(R.id.datePickerButton)
        val timeEditText: EditText = findViewById(R.id.timeEditText)
        val specialRequestsEditText: EditText = findViewById(R.id.specialRequestsEditText)
        val packagesRecyclerView: RecyclerView = findViewById(R.id.packagesRecyclerView)
        val confirmBookingButton: Button = findViewById(R.id.confirmBookingButton)

        // Populate Event Type Spinner
        val eventTypes = arrayOf("Wedding", "Birthday", "Corporate", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, eventTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        eventTypeSpinner.adapter = adapter

        // Set up Date Picker
        val calendar = Calendar.getInstance()
        datePickerButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val date = "$dayOfMonth/${month + 1}/$year"
                    datePickerButton.text = date
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        // Setup RecyclerView
        packagesRecyclerView.layoutManager = LinearLayoutManager(this)
        packagesRecyclerView.adapter = PackagesAdapter(getSamplePackages())

        // Confirm Booking
        confirmBookingButton.setOnClickListener {
            val confirmationMessage = "Booking confirmed for ${eventTypeSpinner.selectedItem} " +
                    "on ${datePickerButton.text} for ${guestsEditText.text} guests."
            Toast.makeText(this, confirmationMessage, Toast.LENGTH_LONG).show()
        }
    }

    // Sample packages
    private fun getSamplePackages(): List<String> {
        return listOf("Silver Package", "Gold Package", "Platinum Package")
    }
}

// RecyclerView Adapter for Packages
class PackagesAdapter(private val packages: List<String>) :
    RecyclerView.Adapter<PackagesAdapter.PackageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return PackageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        holder.textView.text = packages[position]
    }

    override fun getItemCount(): Int = packages.size

    class PackageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)
    }
}
