package com.example.sunsmartfamily

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ActivityB1 : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_b1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.B1)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up the back button to finish the activity
        val backButton = findViewById<TextView>(R.id.addReminder1_backText)
        backButton.setOnClickListener {
            // Finish ActivityA1 and return to the previous Activity
            finish()
        }

        // Find the "Continue" TextView and set a click listener on it
        val continueText = findViewById<TextView>(R.id.addReminder1_continueText)
        continueText.setOnClickListener {
            // Start ActivityB2
            val intent = Intent(this, ActivityB2::class.java)
            startActivity(intent)
        }



        timeDisplay()

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun timeDisplay() {
        val timeTextView = findViewById<TextView>(R.id.addReminder1_timeText)

        // 获取当前的日期和时间
        val now = LocalDateTime.now()

        // 定义日期时间的格式
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        // 格式化日期时间
        val formattedDateTime = now.format(formatter)

        // 将格式化的日期时间设置到TextView
        timeTextView.text = formattedDateTime
    }
}