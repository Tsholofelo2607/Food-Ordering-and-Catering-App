package com.example

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class Bookings : AppCompatActivity() {
    private lateinit var timePicker: TextView
        private lateinit var datePicker: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings)

        val button: View = findViewById(R.id.confirm)
        button.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

       timePicker = findViewById(R.id.timePicker)
       datePicker = findViewById(R.id.datePicker)

        // Handle Date Selection
        timePicker.setOnClickListener {
            showDatePickerDialog()
        }

        // Handle Time Selection
        datePicker.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Format the date to show it nicely
            val formattedDate = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
            datePicker.text = formattedDate  // Update the TextView with the selected date
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            // Format the time to show it nicely
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            timePicker.text= formattedTime
        }, hour, minute, true)

        timePickerDialog.show()
    }
}