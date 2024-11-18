
package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Payment : AppCompatActivity() {
    private lateinit var database: DatabaseReference
private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextCardNumber: EditText
    private lateinit var editTextExpiryDate: EditText
    private lateinit var editTextCvv: EditText
    private lateinit var buttonSubmit: Button
    private lateinit var checkoutSummaryTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)


        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance().getReference("Payments")

        editTextName = findViewById(R.id.nameuser)
        editTextEmail = findViewById(R.id.email)
        editTextPhone = findViewById(R.id.number)
        editTextCardNumber = findViewById(R.id.cardnum)
        editTextExpiryDate = findViewById(R.id.expirydate)
        editTextCvv = findViewById(R.id.cvv)
        buttonSubmit = findViewById(R.id.payment)

        // Initialize checkoutSummaryTextView here
        checkoutSummaryTextView = findViewById(R.id.checkoutSummaryDetails)

        // Retrieve and display checkout summary
        val cartItems = intent.getParcelableArrayListExtra<Menu.FoodItem>("cartItems") ?: emptyList()
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        displayCheckoutSummary(cartItems, totalPrice)

        buttonSubmit.setOnClickListener {
            collectData()
        }
    }
    private fun displayCheckoutSummary(cartItems: List<Menu.FoodItem>, totalPrice: Double) {
        // Initialize the summary string for the checkout items
        val summary = StringBuilder("Checkout Summary:\n")

        // Add cart items to the summary
        for (item in cartItems) {
            summary.append("${item.name} x${item.quantity} - R${item.price * item.quantity}\n")
        }

        // Append the total price
        summary.append("\nTotal: R$totalPrice")

        // Update the checkout summary TextView with the complete string
        checkoutSummaryTextView.text = summary.toString()
    }


    private fun saveToFirebase(name: String, email: String, phone: String, cardNumber: String, expiryDate: String, cvv: String) {
        // Create a unique ID for each payment entry
        val paymentId = database.push().key ?: return

        // Create a Payment object
        val payment = PaymentDetails(name, email, phone, cardNumber, expiryDate, cvv)

        // Save to Firebase
        database.child(paymentId).setValue(payment)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Payment information saved successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to save payment information.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    data class PaymentDetails(
        val name: String,
        val email: String,
        val phone: String,
        val cardNumber: String,
        val expiryDate: String,
        val cvv: String
    )

    private fun collectData() {
        val name = editTextName.text.toString()
        val email = editTextEmail.text.toString()
        val phone = editTextPhone.text.toString()
        val cardNumber = editTextCardNumber.text.toString()
        val expiryDate = editTextExpiryDate.text.toString()
        val cvv = editTextCvv.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() &&
            cardNumber.isNotEmpty() && expiryDate.isNotEmpty() && cvv.isNotEmpty()) {

            // Save to Firebase
            saveToFirebase(name, email, phone, cardNumber, expiryDate, cvv)

            // Navigate to the next activity
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
