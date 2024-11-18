package com.example

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

import java.util.Calendar

class Bookings : AppCompatActivity() {
    private lateinit var timePicker: TextView
    private lateinit var datePicker: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings)

        // Find all required fields and button
        val nameField = findViewById<EditText>(R.id.nameuser)
        val locationField = findViewById<EditText>(R.id.editTextLocation)
        val emailField = findViewById<EditText>(R.id.email)
        val phoneNumberField = findViewById<EditText>(R.id.number)
        val messageField = findViewById<EditText>(R.id.editTextMessage)
        val serviceTypeSpinner = findViewById<Spinner>(R.id.serviceTypeSpinner)
        val guestCountField = findViewById<EditText>(R.id.guestCount)
        timePicker = findViewById(R.id.timePicker)
        datePicker = findViewById(R.id.datePicker)
        val confirmButton: Button = findViewById(R.id.confirm)

        // Populate service type spinner
        val serviceTypes = arrayOf("Wedding", "Corporate Event", "Birthday Party", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, serviceTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        serviceTypeSpinner.adapter = adapter

        confirmButton.setOnClickListener {
            // Retrieve text from fields
            val name = nameField.text.toString().trim()
            val location = locationField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val phoneNumber = phoneNumberField.text.toString().trim()
            val message = messageField.text.toString().trim()
            val serviceType = serviceTypeSpinner.selectedItem.toString()
            val guestCount = guestCountField.text.toString().trim()
            val selectedDate = datePicker.text.toString()
            val selectedTime = timePicker.text.toString()

            // Check if any field is empty
            if (name.isEmpty() || location.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() ||
                message.isEmpty() || guestCount.isEmpty() || selectedDate == "Select Date" || selectedTime == "Select Time") {

                // Display an error message if any field is empty
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Save booking details to Firebase
                saveBookingToFirebase(name, location, email, phoneNumber, message, serviceType, guestCount, selectedDate, selectedTime)

                // Show confirmation message
                Toast.makeText(this, "Your details have been received!", Toast.LENGTH_SHORT).show()

                // Redirect to Menu activity if desired
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
            }
        }

        // Set up the click listeners correctly
        datePicker.setOnClickListener {
            showDatePickerDialog()
        }

        timePicker.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun saveBookingToFirebase(
        name: String,
        location: String,
        email: String,
        phoneNumber: String,
        message: String,
        serviceType: String,
        guestCount: String,
        selectedDate: String,
        selectedTime: String
    ) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("bookings")

        // Create a unique booking ID
        val bookingId = myRef.push().key ?: return // If there's no key, do nothing

        // Create a map to hold the booking details
        val bookingDetails = mapOf(
            "name" to name,
            "location" to location,
            "email" to email,
            "phoneNumber" to phoneNumber,
            "message" to message,
            "serviceType" to serviceType,
            "guestCount" to guestCount,
            "date" to selectedDate,
            "time" to selectedTime
        )

        // Save the booking details to Firebase under the generated booking ID
        myRef.child(bookingId).setValue(bookingDetails)
            .addOnSuccessListener {
                // Display success message if the booking is saved successfully
                Toast.makeText(this, "Booking saved successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                // Display error message if there is an issue saving the booking
                Toast.makeText(this, "Failed to save booking: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(selectedYear, selectedMonth, selectedDay)

            // Check if the selected date is in the past
            if (selectedDate.before(Calendar.getInstance())) {
                Toast.makeText(this, "Cannot select a past date", Toast.LENGTH_SHORT).show()
            } else {
                val formattedDate = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                datePicker.text = formattedDate
            }
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            // Check if the selected time is within the allowed range
            if (selectedHour < 8 || selectedHour > 17) {
                Toast.makeText(this, "Please select a time between 08:00 and 17:00", Toast.LENGTH_SHORT).show()
            } else {
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                timePicker.text = formattedTime
            }
        }, hour, minute, true)

        timePickerDialog.show()
    }
}

