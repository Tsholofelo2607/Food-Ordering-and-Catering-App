package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize views
        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val notificationsSwitch: Switch = findViewById(R.id.notificationsSwitch)
        val darkModeSwitch: Switch = findViewById(R.id.darkModeSwitch)
        val faqButton: Button = findViewById(R.id.faqButton)
        val contactSupportButton: Button = findViewById(R.id.contactSupportButton)
        val logoutButton: Button = findViewById(R.id.logoutButton)

        // Load user preferences (dummy data for now)
        nameEditText.setText("John Doe")
        emailEditText.setText("johndoe@example.com")

        // App Preferences
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Notifications ${if (isChecked) "Enabled" else "Disabled"}", Toast.LENGTH_SHORT).show()
        }

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Help & Support Buttons
        faqButton.setOnClickListener {
            Toast.makeText(this, "FAQ clicked (navigate to FAQ screen)", Toast.LENGTH_SHORT).show()
        }

        contactSupportButton.setOnClickListener {
            Toast.makeText(this, "Contact Support clicked (navigate to Contact Support screen)", Toast.LENGTH_SHORT).show()
        }

        // Logout Button
        logoutButton.setOnClickListener {
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            // Navigate to login screen or perform logout logic
            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
