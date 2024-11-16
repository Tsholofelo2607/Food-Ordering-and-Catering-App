package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var usernameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Bind the views from the layout
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView)
        usernameEditText = findViewById(R.id.usernameEditText)

        //  Onclick listener for Forgot Password
        forgotPasswordTextView.setOnClickListener {
            val email = usernameEditText.text.toString().trim()
            if (email.isEmpty()) {
                promptForPasswordReset() // Show dialog to enter email
            } else {
                sendPasswordResetEmail(email)
            }
        }
    }

    // Function to prompt the user for their email address
    private fun promptForPasswordReset() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Password Reset")

        // Set up an EditText field to enter the email
        val input = EditText(this)
        input.hint = "Enter your email"
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("Send") { dialog, _ ->
            val email = input.text.toString().trim()
            if (email.isNotEmpty()) {
                sendPasswordResetEmail(email)
            } else {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    // Function to send password reset email
    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent to $email", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }

        findViewById<TextView>(R.id.signUpTextView).setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            // Handle login logic here
        }
    }
}
