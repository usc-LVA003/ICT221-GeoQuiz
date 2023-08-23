package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question (R.string.question_australia, true),
        Question (R.string.question_oceans, true),
        Question (R.string.question_mideast, false),
        Question (R.string.question_africa, false),
        Question (R.string.question_americas, true),
        Question (R.string.question_asia, true))

    private var currentIndex = 0

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    var currentQuestionComplete: Boolean = false
        get() = questionBank[currentIndex].completed

    val currentQuestionIndex: Int
        get() = currentIndex

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentIndex = (currentIndex - 1) % questionBank.size
        if (currentIndex < 0) { currentIndex = questionBank.lastIndex}
    }


}