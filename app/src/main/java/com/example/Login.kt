package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        // Get the button by its ID
        val buttonNavigate = findViewById<Button>(R.id.button6)

        // Set an onClickListener on the button
        buttonNavigate.setOnClickListener {
            // Create an intent to navigate to SecondActivity
            val intent = Intent(this, Register::class.java)
            startActivity(intent)  // Start the second activity

        }
    }
}