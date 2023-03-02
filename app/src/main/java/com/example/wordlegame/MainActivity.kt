package com.example.wordlegame

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible

class MainActivity : AppCompatActivity() {
    var wordToGuess = FourLetterWordList.FourLetterWordList.getRandomFourLetterWord()
    val maxGuesses = 3
    var numGuesses = maxGuesses


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var simpleEditText = findViewById<View>(R.id.input) as EditText
        var strValue = simpleEditText.text.toString()
        var correct = findViewById<TextView>(R.id.correct)
        var button = findViewById<Button>(R.id.submit_button)
        var reset = findViewById<Button>(R.id.reset_button)
        reset.setBackgroundColor(Color.GRAY)
        var guesses = findViewById<TextView>(R.id.guesses)


        button.setOnClickListener {
            if(numGuesses > 0) {
                simpleEditText = findViewById<View>(R.id.input) as EditText
                strValue = simpleEditText.text.toString().uppercase()
                if (strValue.length == 4) {
                    guesses.text = "" + guesses.text + strValue + " " + checkGuess(strValue) + " "
                    numGuesses--
                    simpleEditText.text.clear()
                    hideSoftKeyboard(simpleEditText)
                } else {
                    Toast.makeText(it.context, "Enter a 4 letter word", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            if(numGuesses <= 0 || wordToGuess.equals(strValue)){
                button.isInvisible = true
                reset.isInvisible = false
                correct.text = "" + correct.text + wordToGuess
            }
        }

        reset.setOnClickListener {
            guesses.text = ""
            numGuesses = maxGuesses
            button.isInvisible = false
            reset.isInvisible = true
            wordToGuess = FourLetterWordList.FourLetterWordList.getRandomFourLetterWord()
            correct.text = "Correct word was: "
        }
    }

    fun hideSoftKeyboard(view: View) {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }



    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}
