package com.example.sunsmartfamily

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityB2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_b2)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.B2)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 设置"Back"按钮的点击事件
        val backButton = findViewById<TextView>(R.id.addReminder2_backText)
        backButton.setOnClickListener {
            // 结束当前活动，返回到ActivityB1
            finish()
        }


    }
}