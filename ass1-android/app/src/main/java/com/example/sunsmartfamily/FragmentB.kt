package com.example.sunsmartfamily

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class FragmentB : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_b, container, false)

        // Set up the click listener for the "Add Reminder" button
        val addButton: Button = view.findViewById(R.id.reminder_addButton)
        addButton.setOnClickListener {
            // Start ActivityB1
            val intent = Intent(activity, ActivityB1::class.java)
            startActivity(intent)
        }

        return view
    }
}
