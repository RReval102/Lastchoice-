package com.example.moralchoicesgame

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultTextView: TextView = findViewById(R.id.resultTextView)
        val traitScores = mutableMapOf(
            Trait.EMPATHY to 0,
            Trait.SELF_PRESERVATION to 0,
            Trait.UTILITARIANISM to 0
        )

        val traitAdjustments = mapOf(
            Pair(1, 1) to mapOf(Trait.EMPATHY to 2), // Плачущая мать с ребенком
            Pair(1, 2) to mapOf(Trait.SELF_PRESERVATION to 2), // Своя мама
            Pair(1, 3) to mapOf(Trait.UTILITARIANISM to 2), // Доктор
            Pair(1, 4) to mapOf(Trait.UTILITARIANISM to 1), // Молодой гений
            Pair(1, 5) to mapOf(Trait.UTILITARIANISM to 1), // Сильный рабочий
            Pair(2, 1) to mapOf(Trait.UTILITARIANISM to 2), // Да, дернуть рычаг
            Pair(2, 2) to mapOf(Trait.EMPATHY to 2) // Нет, ничего не делать
        )

        for (choice in GameState.choices) {
            for (option in choice.selectedOptions) {
                val adjustments = traitAdjustments[Pair(choice.scenarioId, option)] ?: emptyMap()
                for ((trait, value) in adjustments) {
                    traitScores[trait] = traitScores[trait]!! + value
                }
            }
        }

        val dominantTrait = traitScores.maxByOrNull { it.value }?.key
        val totalTime = GameState.choices.sumOf { it.timeTaken }
        val averageTime = if (GameState.choices.isNotEmpty()) totalTime / GameState.choices.size else 0

        val timeComment = when {
            averageTime < 5000 -> "Вы быстро принимаете решения под давлением."
            averageTime < 10000 -> "Вы обдумываете свои решения."
            else -> "Вы тщательно взвешиваете свои варианты."
        }

        val traitText = when (dominantTrait) {
            Trait.EMPATHY -> "Вы сострадательный человек, ценящий человеческую жизнь."
            Trait.SELF_PRESERVATION -> "Вы ставите свою безопасность и близких на первое место."
            Trait.UTILITARIANISM -> "Вы принимаете решения ради наибольшей пользы."
            else -> "Ваши черты сбалансированы."
        }

        resultTextView.text = "На основе ваших выборов, вы в первую очередь $traitText\n$timeComment"
    }
}