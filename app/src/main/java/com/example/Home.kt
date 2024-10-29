package com.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity



class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val skipbutton: View=findViewById(R.id.skip)
        skipbutton.setOnClickListener{
            val intent= Intent(this, Menu::class.java)
            startActivity(intent)
        }

        val registerbutton: View=findViewById(R.id.Register)
        registerbutton.setOnClickListener{
            val intent= Intent(this, Register::class.java)
           startActivity(intent)
        }

        val button: View=findViewById(R.id.Login)
        button.setOnClickListener{
            val intent= Intent(this, Login::class.java)
            startActivity(intent)

            /*val viewPager: ViewPager2 = findViewById(R.id.viewPager)

            val cardItems = listOf(
                CardItem(R.drawable.catering, "Description for Image 1"),
                CardItem(R.drawable.foodorder, "Description for Image 2"),
                CardItem(R.drawable.offers, "Description for Image 3")

            )

            viewPager.adapter = CardAdapter(cardItems)*/
        }
    }
        }


