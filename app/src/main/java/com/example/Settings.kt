package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Sign In button
        val buttonSignIn: Button = findViewById(R.id.signres)
        buttonSignIn.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        // Personal Details button
        val buttonPersonalDetails: Button = findViewById(R.id.personal)
        buttonPersonalDetails.setOnClickListener {
            val intent = Intent(this, PersonalDetails::class.java)
            startActivity(intent)
        }

        // Order History button
        val buttonOrderHistory: Button = findViewById(R.id.history)
        buttonOrderHistory.setOnClickListener {
            val intent = Intent(this, OrderHistory::class.java)
            startActivity(intent)
        }
    }
}
