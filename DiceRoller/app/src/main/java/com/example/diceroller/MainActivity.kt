package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var diceImage1 : ImageView
    lateinit var diceImage2 : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.roll_button)
        val resetButton: Button = findViewById(R.id.reset_button)
        rollButton.setOnClickListener { rollDice() }
        resetButton.setOnClickListener { reset() }
        diceImage1 = findViewById(R.id.dice_image1)
        diceImage2 = findViewById(R.id.dice_image2)
    }

    private fun rollDice() {
        diceImage1.setImageResource(getRandomDiceImage())
        diceImage2.setImageResource(getRandomDiceImage())
    }

    private fun getRandomDiceImage() : Int {
        return when ((1..6).random()) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    private fun reset() {
        val diceImage1: ImageView = findViewById(R.id.dice_image1)
        val drawableResource1 = R.drawable.empty_dice
        diceImage1.setImageResource(drawableResource1)

        val diceImage2: ImageView = findViewById(R.id.dice_image2)
        val drawableResource2 = R.drawable.empty_dice
        diceImage2.setImageResource(drawableResource2)
    }
}