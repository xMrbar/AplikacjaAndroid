package com.example.aplikacjaandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val button1 = findViewById<Button>(R.id.buttonCodziennePlatnosci)
        val button2 = findViewById<Button>(R.id.buttonPlanowanieBudzetu)
        val button3 = findViewById<Button>(R.id.buttonStatystyki)
        val button4 = findViewById<Button>(R.id.buttonZarzadanie)

        button1.setOnClickListener {

        }

        button2.setOnClickListener {
            val intentButtonPBA = Intent(this, PlanowanieBudzetuActivity::class.java)
            startActivity(intentButtonPBA)
        }

        button3.setOnClickListener {

        }

        button4.setOnClickListener {

        }
    }
}