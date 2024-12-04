package com.example

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val nameInput = findViewById<EditText>(R.id.nameEditText)
        val surnameInput = findViewById<EditText>(R.id.surnameEditText)
        val emailInput = findViewById<EditText>(R.id.emailEditText)
        val passwordInput = findViewById<EditText>(R.id.passwordEditText)
        val confirmPasswordInput = findViewById<EditText>(R.id.confirmPasswordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val surname = surnameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = confirmPasswordInput.text.toString().trim()
            val cbTerms = findViewById<CheckBox>(R.id.cbTerms)

            if (!cbTerms.isChecked) {
                Toast.makeText(this, "Please accept Terms and Conditions", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            } else if (!isPasswordValid(password)) {
                Toast.makeText(
                    this,
                    "Password must be at least 8 characters long and include uppercase, lowercase, numbers, and symbols.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(email, password, name, surname)
            }
        }
        findViewById<TextView>(R.id.signInTextView).setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        findViewById<Button>(R.id.registerButton).setOnClickListener {
            // Handle registration logic here
        }

}
    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +        // at least 1 digit
                    "(?=.*[a-z])" +        // at least 1 lowercase letter
                    "(?=.*[A-Z])" +        // at least 1 uppercase letter
                    "(?=.*[@#\$%^&+=!])" + // at least 1 special character
                    "(?=\\S+$)" +          // no whitespace
                    ".{8,}" +              // at least 8 characters
                    "$"
        )
        return passwordPattern.matcher(password).matches()
    }

    private fun registerUser(email: String, password: String, name: String, surname: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        saveUserData(userId, name, surname, email)
                    }
                } else {
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserData(userId: String, name: String, surname: String, email: String) {
        val user = User(name, surname, email)
        database.child("users").child(userId).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    // Navigate to login or main screen
                } else {
                    Toast.makeText(this, "Failed to save user data: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

data class User(val name: String, val surname: String, val email: String)


