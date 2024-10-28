
package com.example

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Payment : AppCompatActivity() {
private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextCardNumber: EditText
    private lateinit var editTextExpiryDate: EditText
    private lateinit var editTextCvv: EditText
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)


        editTextName = findViewById(R.id.name)
        editTextEmail = findViewById(R.id.email)
        editTextPhone = findViewById(R.id.number)
        editTextCardNumber = findViewById(R.id.cardnum)
        editTextExpiryDate = findViewById(R.id.expirydate)
        editTextCvv = findViewById(R.id.cvv)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            collectData()
        }
    }

    private fun collectData() {
        val name = editTextName.text.toString()
        val email = editTextEmail.text.toString()
        val phone = editTextPhone.text.toString()
        val cardNumber = editTextCardNumber.text.toString()
        val expiryDate = editTextExpiryDate.text.toString()
        val cvv = editTextCvv.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() &&
            cardNumber.isNotEmpty() && expiryDate.isNotEmpty() && cvv.isNotEmpty()) {

            // Here you would handle the collected data (e.g., save to a database, send to a server)
            Toast.makeText(this, "Data Collected:\nName: $name\nEmail: $email\nPhone: $phone\nCard Number: $cardNumber\nExpiry Date: $expiryDate\nCVV: $cvv", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}

