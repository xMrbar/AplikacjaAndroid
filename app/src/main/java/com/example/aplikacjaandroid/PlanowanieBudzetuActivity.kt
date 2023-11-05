package com.example.aplikacjaandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PlanowanieBudzetuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planbudzetu)

        val buttonPrzychody = findViewById<Button>(R.id.buttonPrzychody_PB1)
        buttonPrzychody.setOnClickListener {
            val intentButton = Intent(this, PlanowanieBudzetuPrzychodyActivity::class.java)
            startActivity(intentButton)
            finish()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}