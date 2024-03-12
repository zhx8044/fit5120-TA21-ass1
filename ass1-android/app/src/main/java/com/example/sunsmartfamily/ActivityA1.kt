package com.example.sunsmartfamily

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityA1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_a1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.A1)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up the back button to finish the activity
        val backButton = findViewById<TextView>(R.id.cur_index_backText)
        backButton.setOnClickListener {
            // Finish ActivityA1 and return to the previous Activity
            finish()
        }
    }
}