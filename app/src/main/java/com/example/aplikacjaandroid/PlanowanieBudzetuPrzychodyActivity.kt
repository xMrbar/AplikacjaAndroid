package com.example.aplikacjaandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PlanowanieBudzetuPrzychodyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planbudzetuprzychody)

        val buttonWydatki = findViewById<Button>(R.id.buttonWydatki_PB2)
        buttonWydatki.setOnClickListener {
            val intentButton1 = Intent(this, PlanowanieBudzetuActivity::class.java)
            startActivity(intentButton1)
            finish()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}