package com.example.moralchoicesgame

object GameState {
    val choices = mutableListOf<Choice>()

    fun clear() {
        choices.clear()
    }
}

data class Choice(val scenarioId: Int, val selectedOptions: List<Int>, val timeTaken: Long)