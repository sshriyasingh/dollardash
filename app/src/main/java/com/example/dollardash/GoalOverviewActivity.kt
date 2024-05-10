package com.example.dollardash


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View

class GoalOverviewActivity : AppCompatActivity() {
    private var currentProgress : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_overview)

        var sharedPreferencesManager = SharedPreferencesManager(this)

        val curr = intent
        val username = curr.getStringExtra("username")

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val textViewProgress = findViewById<TextView>(R.id.currContribution)
        val editTextNumber = findViewById<EditText>(R.id.inputtedContribution)
        val buttonUpdate = findViewById<Button>(R.id.enterContribution)
        val goalName = findViewById<TextView>(R.id.goalName)

        // Maximum value for the progress bar
        val maxProgress = intent.getIntExtra("goalAmount", 0)

        Log.w("MaxProgress", maxProgress.toString())
        goalName.text = intent.getStringExtra("goalName")
        progressBar.max = maxProgress  // You might need to adjust your custom progress drawable
        currentProgress = intent.getIntExtra("progress", 0)
        progressBar.progress = currentProgress

        val percentageView = findViewById<TextView>(R.id.currPercentage)
        var percentage = (currentProgress.toFloat() / maxProgress.toFloat() * 100).toInt()
        percentageView.text = "$percentage%"

        textViewProgress.text = "$$currentProgress/$${maxProgress.toString()}"
        buttonUpdate.setOnClickListener {
            val contribution = editTextNumber.text.toString().toIntOrNull() ?: 0
            sharedPreferencesManager.saveAccountBalance(username.toString(), sharedPreferencesManager.getAccountBalance(username.toString()))
            //val
            val newProgress = currentProgress + contribution
            progressBar.progress = newProgress
            currentProgress = newProgress
            textViewProgress.text = "$$newProgress/$maxProgress"
            editTextNumber.text.clear()
            percentage = (newProgress.toFloat() / maxProgress.toFloat() * 100).toInt()
            percentageView.text = "$percentage%"
        }
    }
    fun goBack(view: View) {
        val resultIntent = Intent().apply {
            putExtra("progress", currentProgress)
            putExtra("goalIndex", intent.getIntExtra("goalIndex", -1))  // Get the passed index back
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
