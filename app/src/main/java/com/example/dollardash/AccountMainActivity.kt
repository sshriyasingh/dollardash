package com.example.dollardash

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class AccountMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_main)

        var firebase : FirebaseDatabase = FirebaseDatabase.getInstance( )
        var reference : DatabaseReference = firebase.getReference()
        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val calendar = Calendar.getInstance()
        datePicker.minDate = calendar.timeInMillis
        val inputtedBalance = findViewById<EditText>(R.id.inputtedBalance)
        val updateBalance = findViewById<Button>(R.id.updateBalance)
        var balance = 0
        val currBalance = findViewById<TextView>(R.id.currBalance)
        val createGoalButton = findViewById<TextView>(R.id.createGoalButton)

        updateBalance.setOnClickListener() {
            val inputStr = inputtedBalance.text.toString()
            if (inputStr.isNotEmpty()) {
                val number = inputStr.toInt()
                balance += number
                currBalance.text = "Your Current Balance: \$" + balance
            }
        }
        createGoalButton.setOnClickListener() {
            val intent = Intent(this, GoalOverviewActivity::class.java)
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            startActivity(intent)
            //currently login button does not work when you "go back to login button"
        }
        val logoutButton = findViewById<Button>(R.id.logout)
        logoutButton.setOnClickListener() {
            FirebaseAuth.getInstance().signOut()
            finish()
        }

    }

}