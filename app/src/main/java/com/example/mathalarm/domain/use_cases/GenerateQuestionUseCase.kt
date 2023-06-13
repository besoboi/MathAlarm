package com.example.mathalarm.domain.use_cases

import com.example.mathalarm.domain.Question
import com.example.mathalarm.domain.QuestionRepository
import javax.inject.Inject

class GenerateQuestionUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) {

    operator fun invoke(maxValue: Int): Question = questionRepository.generateQuestion(
        maxValue,
        MIN_SUM_VALUE,
        MIN_VISIBLE_NUMBER_VALUE,
        COUNT_OF_OPTIONS
    )

    companion object {

        private const val MIN_SUM_VALUE = 2
        private const val MIN_VISIBLE_NUMBER_VALUE = 1
        private const val COUNT_OF_OPTIONS = 6
    }
}