package com.example.mathalarm.domain

interface QuestionRepository {
    fun generateQuestion(
        maxValue: Int,
        minSumValue: Int,
        minAnswerValue: Int,
        countOfOptions: Int
    ): Question
}