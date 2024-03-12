package com.example.sunsmartfamily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import android.content.Intent




class FragmentA : Fragment() {
    private lateinit var stateSpinner: Spinner
    private lateinit var suburbSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_a, container, false)

        // Initialize your spinners here
        stateSpinner = view.findViewById(R.id.spinner_state)
        suburbSpinner = view.findViewById(R.id.spinner_suburb)

        // Initialize the state spinner with values from strings.xml
        val statesAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.states,
            android.R.layout.simple_spinner_item
        )
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateSpinner.adapter = statesAdapter

        // When a state is selected, update the suburb spinner
        stateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Use the selected state to determine which suburb array to use
                val selectedState = parent.getItemAtPosition(position) as String
                val suburbsArrayId = when (selectedState) {
                    "Western Australia" -> R.array.western_australia_suburbs
                    "Northern Territory" -> R.array.northern_territory_suburbs
                    "South Australia" -> R.array.south_australia_suburbs
                    "Queensland" -> R.array.queensland_suburbs
                    "New South Wales" -> R.array.new_south_wales_suburbs
                    "Victoria" -> R.array.victoria_suburbs
                    "Tasmania" -> R.array.tasmania_suburbs
                    else -> 0
                }

                if (suburbsArrayId != 0) {
                    val suburbsAdapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        suburbsArrayId,
                        android.R.layout.simple_spinner_item
                    )
                    suburbsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    suburbSpinner.adapter = suburbsAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Can leave this empty
            }
        }

        // Setup the button click to navigate to ActivityA1
        val button: Button = view.findViewById(R.id.gotoUVIndexScreenButton)
        button.setOnClickListener {
            startActivity(Intent(activity, ActivityA1::class.java))
        }

// Setup the back button click to navigate back to HomeFragment


        return view

    }


}
