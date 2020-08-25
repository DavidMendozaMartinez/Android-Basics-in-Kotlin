package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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
        rollButton.setOnClickListener { rollDice() }

        // Do a dice roll when the app starts
        rollDice()
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

        // Find the ImageViews in the layout
        val firstDiceImage: ImageView = findViewById(R.id.imageViewFirstDice)
        val secondDiceImage: ImageView = findViewById(R.id.imageViewSecondDice)

        // Get the drawable resource IDs to use based on the dice roll
        val firstDrawableResource = getDrawableResourceID(firstDiceRoll)
        val secondDrawableResource = getDrawableResourceID(secondDiceRoll)

        // Update the ImageViews with the correct drawable resource IDs
        firstDiceImage.setImageResource(firstDrawableResource)
        secondDiceImage.setImageResource(secondDrawableResource)

        // Update the content descriptions
        firstDiceImage.contentDescription = firstDiceRoll.toString()
        secondDiceImage.contentDescription = secondDiceRoll.toString()
    }

    /**
     * Determine which drawable resource ID to use based on the dice roll.
     */
    private fun getDrawableResourceID(diceRoll: Int) =
        when (diceRoll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
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