package com.example.mathalarm.data

import com.example.mathalarm.domain.Question
import com.example.mathalarm.domain.QuestionRepository
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class QuestionRepositoryImpl @Inject constructor() : QuestionRepository {
    override fun generateQuestion(
        maxValue: Int,
        minSumValue: Int,
        minAnswerValue: Int,
        countOfOptions: Int
    ): Question {
        val sum = Random.nextInt(minSumValue, maxValue + 1)
        val visibleNumber = Random.nextInt(minAnswerValue, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(minAnswerValue, rightAnswer - countOfOptions)
        val to = min(rightAnswer + countOfOptions, maxValue)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to + 1))
        }
        return Question(sum, visibleNumber, options.toList())
    }
}