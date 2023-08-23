package com.bignerdranch.android.geoquiz


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val questionBank = listOf(
        Question (R.string.question_australia, true),
        Question (R.string.question_oceans, true),
        Question (R.string.question_mideast, false),
        Question (R.string.question_africa, false),
        Question (R.string.question_americas, true),
        Question (R.string.question_asia, true))
    private var currentIndex = 0
    private var currentScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener {view: View ->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {view: View ->
            checkAnswer(false)
        }

        binding.previousButton.setOnClickListener {
            currentIndex = (currentIndex - 1) % questionBank.size
            if (currentIndex < 0) { currentIndex = questionBank.lastIndex}
            questionAnswered(currentIndex)
            updateQuestion()
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            questionAnswered(currentIndex)
            updateQuestion()
        }

        binding.questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
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
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
        questionBank[currentIndex].completed = true
        val correctAnswer = questionBank[currentIndex].answer
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
        if (currentIndex == 5) {
            var score = currentScore*100/questionBank.size
            val message = getString(R.string.toast_score, score)
            val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            toast.show()
        }


    }

    private fun questionAnswered(index:Int){
        val isQuestionAnswered = questionBank[index].completed
        binding.trueButton.isEnabled = !isQuestionAnswered
        binding.falseButton.isEnabled = !isQuestionAnswered

    }

}