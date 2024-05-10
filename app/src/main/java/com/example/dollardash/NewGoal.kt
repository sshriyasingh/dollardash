package com.example.dollardash

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class NewGoal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal_page)

        val enterGoalName = findViewById<EditText>(R.id.enterGoalName)
        val enterGoalAmount = findViewById<EditText>(R.id.enterGoalAmount)
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val calendar = Calendar.getInstance()

        datePicker.minDate = calendar.timeInMillis
        val enterButton = findViewById<Button>(R.id.enterButton)

        enterButton.setOnClickListener {
            val goalName = enterGoalName.text.toString()
            //val goalAmount = enterGoalAmount.text.toString().toDoubleOrNull() ?: 0.0
            if (goalName.isNotEmpty() && enterGoalAmount.text.toString().isNotEmpty()) {
                val goalAmount = enterGoalAmount.text.toString().toDouble()
                if (goalAmount > 0) {
                    val selectedDate = calendar.time
                    val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate)
                    val progress = 0.0
                    val resultIntent = Intent()
                    resultIntent.putExtra("goalName", goalName)
                    resultIntent.putExtra("goalAmount", goalAmount)
                    resultIntent.putExtra("selectedDate", formattedDate)
                    resultIntent.putExtra("progress", progress)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
                else {
                    Toast.makeText(this, "Enter a Goal Amount > 0", Toast.LENGTH_LONG).show()
                }
            } else {
                if (goalName.isEmpty()){
                    Toast.makeText(this, "Enter a longer Goal Name", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
