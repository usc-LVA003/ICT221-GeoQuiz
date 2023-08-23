package com.bignerdranch.android.geoquiz


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private var currentScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            questionAnswered()
            updateQuestion()
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            questionAnswered()
            updateQuestion()
        }

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
        quizViewModel.questionComplete()
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            currentScore++
            R.string.correct
        } else {
            R.string.incorrect
        }
        val linearLayout = findViewById<View>(R.id.linearLayout)
        val snackbar = Snackbar.make(linearLayout, messageResId, Snackbar.LENGTH_SHORT)
        snackbar.setAction("Dismiss") {
            snackbar.dismiss()
        }
        snackbar.show()
        calculateScore()
    }

    private fun calculateScore() {
        if (quizViewModel.currentQuestionIndex == 5) {
            var score = currentScore * 100 / quizViewModel.questionBankLength
            val message = getString(R.string.toast_score, score)
            val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            toast.show()
        }


    }

    private fun questionAnswered() {
        val isQuestionAnswered = quizViewModel.currentQuestionComplete
        binding.trueButton.isEnabled = !isQuestionAnswered
        binding.falseButton.isEnabled = !isQuestionAnswered

    }

}