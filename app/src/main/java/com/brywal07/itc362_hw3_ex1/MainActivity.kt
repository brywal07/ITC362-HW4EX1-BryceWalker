package com.brywal07.itc362_hw3_ex1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.brywal07.itc362_hw3_ex1.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private var numCorrect = 0.0


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called)")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")
        binding.trueButton.setOnClickListener { view: View ->
            if(quizViewModel.currentIndex==quizViewModel.getQuestionBank().size - 1){
                quizViewModel.currentQuestion.isAnswered = true
                checkAnswer(true)
                var score = String.format("%.1f",getScore())
                score = score.plus(" %")
                Toast.makeText(this, "Your score is: ".plus(score), Toast.LENGTH_LONG).show()
                updateQuestion()
                reset()
            }else {
                quizViewModel.currentQuestion.isAnswered = true
                checkAnswer(true)
                updateQuestion()
            }
        }
        binding.falseButton.setOnClickListener { view: View ->
            if(quizViewModel.currentIndex==quizViewModel.getQuestionBank().size - 1){
                quizViewModel.currentQuestion.isAnswered = true
                checkAnswer(false)
                var score = String.format("%.1f",getScore())
                score = score.plus(" %")
                Toast.makeText(this, "Your score is: ".plus(score), Toast.LENGTH_LONG).show()
                updateQuestion()
                reset()

            }else {
                quizViewModel.currentQuestion.isAnswered = true
                checkAnswer(false)
                updateQuestion()
            }


        }
        binding.nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            quizViewModel.currentQuestion
            updateQuestion()
        }
        binding.previousButton.setOnClickListener{
            if(quizViewModel.currentIndex > 0) {
                quizViewModel.moveToPrev()
                quizViewModel.currentQuestion
                updateQuestion()
            }else{
                Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show()
            }
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart(Bundle?) called)")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume(Bundle?) called)")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause(Bundle?) called)")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop(Bundle?) called)")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy(Bundle?) called)")
    }
    private fun updateQuestion(){
        binding.trueButton.isEnabled = !quizViewModel.currentQuestion.isAnswered
        binding.falseButton.isEnabled = !quizViewModel.currentQuestion.isAnswered
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if(userAnswer == correctAnswer){
            R.string.correct_snack
        }else{
            R.string.incorrect_snack
        }
        if (userAnswer == correctAnswer){
            numCorrect ++
        }else{
            numCorrect = numCorrect
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
    private fun getScore():Double{
        return (numCorrect / quizViewModel.getQuestionBank().size)*100
    }
    private fun reset(){
        for (i in quizViewModel.getQuestionBank()){
            i.isAnswered=false
            numCorrect = 0.0
        }
    }
}