package com.example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button: View = findViewById(R.id.login)
        button.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

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