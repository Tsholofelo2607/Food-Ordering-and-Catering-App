package com.example

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class Bookings : AppCompatActivity() {
    private lateinit var timePicker: TextView
    private lateinit var datePicker: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings)

        // Find all required fields and button
        val nameField = findViewById<EditText>(R.id.name)
        val locationField = findViewById<EditText>(R.id.editTextLocation)
        val emailField = findViewById<EditText>(R.id.email)
        val phoneNumberField = findViewById<EditText>(R.id.number)
        val messageField = findViewById<EditText>(R.id.editTextMessage)
        timePicker = findViewById(R.id.timePicker)
        datePicker = findViewById(R.id.datePicker)
        val confirmButton: Button = findViewById(R.id.confirm)

        confirmButton.setOnClickListener {
            // Retrieve text from fields
            val name = nameField.text.toString().trim()
            val location = locationField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val phoneNumber = phoneNumberField.text.toString().trim()
            val message = messageField.text.toString().trim()
            val selectedDate = datePicker.text.toString()
            val selectedTime = timePicker.text.toString()

            // Check if any field is empty
            if (name.isEmpty() || location.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() ||
                message.isEmpty() || selectedDate == "Select Date" || selectedTime == "Select Time") {

                // Display an error message if any field is empty
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Show confirmation message
                Toast.makeText(this, "Your details have been received!", Toast.LENGTH_SHORT).show()

                // Redirect to Menu activity if desired
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
            }
        }


        datePicker.setOnClickListener {
            showDatePickerDialog()
        }

        timePicker.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
            datePicker.text = formattedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            timePicker.text = formattedTime
        }, hour, minute, true)

        timePickerDialog.show()
    }
}
