package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows the user to roll two dice and view the results
 * on the screen.
 */
class MainActivity : AppCompatActivity() {

    /**
     * This method is called when the Activity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the Button in the layout
        val rollButton: Button = findViewById(R.id.button)

        // Set a click listener on the button to roll the dice when the user taps the button
        rollButton.setOnClickListener { rollDice() }
    }

    /**
     * Roll the dice and update the screen with the results.
     */
    private fun rollDice() {
        // Create two Dice objects with 6 sides and roll them
        val firstDice = Dice(6)
        val secondDice = Dice(6)

        val firstDiceRoll = firstDice.roll()
        val secondDiceRoll = secondDice.roll()

        // Update the screen with the dice rolls
        val firstResultTextView: TextView = findViewById(R.id.textViewFirstDice)
        val secondResultTextView: TextView = findViewById(R.id.textViewSecondDice)

        firstResultTextView.text = firstDiceRoll.toString()
        secondResultTextView.text = secondDiceRoll.toString()
    }
}

/**
 * Dice with a fixed number of sides.
 */
class Dice(private val numSides: Int) {

    /**
     * Do a random dice roll and return the result.
     */
    fun roll(): Int {
        return (1..numSides).random()
    }
}