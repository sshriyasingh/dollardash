package com.example.dollardash

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GoalOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_overview)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val textViewProgress = findViewById<TextView>(R.id.currContribution)
        val editTextNumber = findViewById<EditText>(R.id.inputtedContribution)
        val buttonUpdate = findViewById<Button>(R.id.backToLoginButton)

        // Maximum value for the progress bar
        val maxProgress = 2000
        progressBar.max = maxProgress  // You might need to adjust your custom progress drawable
        var currentProgress = 500
        buttonUpdate.setOnClickListener {
            val contribution = editTextNumber.text.toString().toIntOrNull() ?: 0
            //val
            val newProgress = currentProgress + contribution
            progressBar.progress = newProgress
            currentProgress = newProgress
            textViewProgress.text = "$$newProgress/$maxProgress"
            editTextNumber.text.clear()

            // Update percentage TextView
            val percentageView = findViewById<TextView>(R.id.currPercentage)
            val percentage = (newProgress.toFloat() / maxProgress.toFloat() * 100).toInt()
            percentageView.text = "$percentage%"
        }
    }
}
