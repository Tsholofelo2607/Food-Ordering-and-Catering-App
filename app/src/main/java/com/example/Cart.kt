package com.example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Cart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val button: View =findViewById(R.id.start)
        button.setOnClickListener{
            val intent= Intent(this, Menu::class.java)
            startActivity(intent)
        }
    }
    }
