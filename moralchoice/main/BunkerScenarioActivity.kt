package com.example.moralchoicesgame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BunkerScenarioActivity : AppCompatActivity() {
    private lateinit var timerTextView: TextView
    private lateinit var countDownTimer: CountDownTimer
    private var startTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bunker_scenario)

        timerTextView = findViewById(R.id.timerTextView)
        val confirmButton: Button = findViewById(R.id.confirmButton)
        val checkBox1: CheckBox = findViewById(R.id.checkBox1).apply { tag = 1 }
        val checkBox2: CheckBox = findViewById(R.id.checkBox2).apply { tag = 2 }
        val checkBox3: CheckBox = findViewById(R.id.checkBox3).apply { tag = 3 }
        val checkBox4: CheckBox = findViewById(R.id.checkBox4).apply { tag = 4 }
        val checkBox5: CheckBox = findViewById(R.id.checkBox5).apply { tag = 5 }

        startTime = System.currentTimeMillis()
        startTimer()

        confirmButton.setOnClickListener {
            val selectedOptions = mutableListOf<Int>()
            if (checkBox1.isChecked) selectedOptions.add(checkBox1.tag as Int)
            if (checkBox2.isChecked) selectedOptions.add(checkBox2.tag as Int)
            if (checkBox3.isChecked) selectedOptions.add(checkBox3.tag as Int)
            if (checkBox4.isChecked) selectedOptions.add(checkBox4.tag as Int)
            if (checkBox5.isChecked) selectedOptions.add(checkBox5.tag as Int)

            if (selectedOptions.size == 3) {
                countDownTimer.cancel()
                proceed(selectedOptions)
            } else {
                Toast.makeText(this, "Пожалуйста, выберите ровно 3 человека", Toast.LENGTH_SHORT).show()
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
        val choice = Choice(scenarioId = 1, selectedOptions = selectedOptions, timeTaken = timeTaken)
        GameState.choices.add(choice)
        startActivity(Intent(this, TrolleyScenarioActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}