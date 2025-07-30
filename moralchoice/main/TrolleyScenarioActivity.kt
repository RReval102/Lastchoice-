package com.example.moralchoicesgame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TrolleyScenarioActivity : AppCompatActivity() {
    private lateinit var timerTextView: TextView
    private lateinit var countDownTimer: CountDownTimer
    private var startTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trolley_scenario)

        timerTextView = findViewById(R.id.timerTextView)
        val confirmButton: Button = findViewById(R.id.confirmButton)
        val radioYes: RadioButton = findViewById(R.id.radioYes).apply { tag = 1 }
        val radioNo: RadioButton = findViewById(R.id.radioNo).apply { tag = 2 }

        startTime = System.currentTimeMillis()
        startTimer()

        confirmButton.setOnClickListener {
            val selectedOption = when {
                radioYes.isChecked -> listOf(radioYes.tag as Int)
                radioNo.isChecked -> listOf(radioNo.tag as Int)
                else -> emptyList()
            }
            if (selectedOption.isNotEmpty()) {
                countDownTimer.cancel()
                proceed(selectedOption)
            }
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = "Осталось времени: ${millisUntilFinished / 1000}с"
            }

            override fun onFinish() {
                proceed(emptyList())
            }
        }.start()
    }

    private fun proceed(selectedOptions: List<Int>) {
        val timeTaken = System.currentTimeMillis() - startTime
        val choice = Choice(scenarioId = 2, selectedOptions = selectedOptions, timeTaken = timeTaken)
        GameState.choices.add(choice)
        startActivity(Intent(this, ResultActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}