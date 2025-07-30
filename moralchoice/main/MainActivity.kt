package com.example.moralchoicesgame

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton: Button = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            GameState.clear() // Очищаем состояние перед началом
            startActivity(Intent(this, BunkerScenarioActivity::class.java))
        }
    }
}