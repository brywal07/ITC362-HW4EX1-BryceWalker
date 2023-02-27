package com.brywal07.itc362_hw3_ex1

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel(){
    private val questionBank = listOf(
        Question(R.string.question_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false)
    )
    internal var currentIndex
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?:0
            set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    var currentQuestion: Question =  questionBank[currentIndex]
        get() = questionBank[currentIndex]
    fun moveToNext(){
        currentIndex = (currentIndex + 1) %questionBank.size
    }
    fun moveToPrev(){
        currentIndex = (currentIndex - 1) % questionBank.size
    }
    fun getQuestionBank(): List<Question> {
        return questionBank
    }
}