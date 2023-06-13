package com.example.mathalarm.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mathalarm.domain.Question
import com.example.mathalarm.domain.use_cases.GenerateQuestionUseCase
import javax.inject.Inject

class MathViewModel @Inject constructor(
    private val generateQuestionUseCase: GenerateQuestionUseCase
) : ViewModel() {

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(MAX_VALUE)
    }

    fun startGame(){
        generateQuestion()
    }

    fun chooseAnswer(answer: Int) : Boolean{
        val rightAnswer = question.value
        return if (answer == rightAnswer?.rightAnswer) {
            true
        } else {
            generateQuestion()
            false
        }
    }

    companion object {
        private const val MAX_VALUE = 100
    }
}