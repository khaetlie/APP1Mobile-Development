package com.example.planetweight

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showWelcomePopup() // Welcome popup at launch

        val weightInput = findViewById<EditText>(R.id.weightInput)
        val planetSpinner = findViewById<Spinner>(R.id.planetSpinner)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        val planets = listOf("Mercury", "Venus", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto", "Moon")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, planets)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        planetSpinner.adapter = adapter

        val gravityMap = mapOf(
            "Mercury" to 0.38,
            "Venus" to 0.91,
            "Mars" to 0.38,
            "Jupiter" to 2.34,
            "Saturn" to 0.93,
            "Uranus" to 0.92,
            "Neptune" to 1.12,
            "Pluto" to 0.06,
            "Moon" to 0.165
        )

        calculateButton.setOnClickListener {
            val weightStr = weightInput.text.toString().trim()
            val selectedPlanet = planetSpinner.selectedItem?.toString()

            if (weightStr.isEmpty()) {
                resultText.text = "Please enter your weight."
                resultText.visibility = TextView.VISIBLE
                return@setOnClickListener
            }

            try {
                val weight = weightStr.toDouble()
                val gravity = gravityMap[selectedPlanet]

                if (gravity != null) {
                    val result = weight * gravity
                    resultText.text = "\n\nYour weight on $selectedPlanet is\n${"%.2f".format(result)} kg"
                } else {
                    resultText.text = "Invalid planet selected."
                }

                resultText.visibility = TextView.VISIBLE
            } catch (e: NumberFormatException) {
                resultText.text = "Please enter a valid number."
                resultText.visibility = TextView.VISIBLE
            }
        }
    }

    //  Welcome popup function
    private fun showWelcomePopup() {
        val dialogView = layoutInflater.inflate(R.layout.popup_welcome, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val continueButton = dialogView.findViewById<Button>(R.id.continueButton)
        continueButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
