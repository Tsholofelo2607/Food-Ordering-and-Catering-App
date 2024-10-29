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
        // Find the logout button
        val logoutButton: Button = findViewById(R.id.logout)

        // Set an OnClickListener for the logout button
        logoutButton.setOnClickListener {
            // Handle logout logic here
            // For example, clear user session data or shared preferences
            // Then redirect to the login activity or main activity
            val intent = Intent(this, Login::class.java) // Replace with your actual login activity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Finish the current activity if you want to remove it from the back stack
        }
    }
}
