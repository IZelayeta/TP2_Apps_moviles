package com.example.punto1

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var currentScore = 0
    private var highScore = 0
    private var failedAttempts = 0
    private var randomNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // declaro el  SharedPreferences
        sharedPreferences = getSharedPreferences("PREFS", MODE_PRIVATE)
        highScore = sharedPreferences.getInt("HIGH_SCORE", 0)

        // Referencias a los elementos de la UI
        val tvCurrentScore: TextView = findViewById(R.id.tv_current_score)
        val tvHighScore: TextView = findViewById(R.id.tv_high_score)
        val tvAttempts: TextView = findViewById(R.id.tv_attempts)
        val etUserInput: EditText = findViewById(R.id.et_user_input)
        val btnSubmit: Button = findViewById(R.id.btn_submit)

        // se ve el puntaje max
        tvHighScore.text = "Mejor puntaje: $highScore"
        tvCurrentScore.text = "Puntaje actual: $currentScore"

        // tira un nums aleatorios
        generateRandomNumber()

        btnSubmit.setOnClickListener {
            val userInput = etUserInput.text.toString().toIntOrNull()

            if (userInput != null && userInput in 1..5) {
                if (userInput == randomNumber) {
                    currentScore += 10
                    tvCurrentScore.text = "Puntaje actual: $currentScore"
                    Toast.makeText(this, "¡Adivinaste!", Toast.LENGTH_SHORT).show()
                    if (currentScore > highScore) {
                        highScore = currentScore
                        sharedPreferences.edit().putInt("HIGH_SCORE", highScore).apply()
                        tvHighScore.text = "Mejor puntaje: $highScore"
                    }
                    generateRandomNumber()
                    failedAttempts = 0
                } else {
                    failedAttempts++
                    tvAttempts.text = "Intentos fallidos: $failedAttempts"
                    Toast.makeText(this, "Fallaste, intenta de nuevo", Toast.LENGTH_SHORT).show()
                    if (failedAttempts >= 5) {
                        currentScore = 0
                        tvCurrentScore.text = "Puntaje actual: $currentScore"
                        Toast.makeText(this, "¡Perdiste el juego!", Toast.LENGTH_SHORT).show()
                        failedAttempts = 0
                        tvAttempts.text = "Intentos fallidos: $failedAttempts"
                        generateRandomNumber()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, ingresa un número entre 1 y 5", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateRandomNumber() {
        randomNumber = Random.nextInt(1, 6)
    }
}