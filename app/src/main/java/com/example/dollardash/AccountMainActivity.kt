package com.example.dollardash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Locale

class AccountMainActivity : AppCompatActivity() {
    private lateinit var inputtedBalance: EditText
    private lateinit var createGoalButton: Button
    private lateinit var updateBalanceButton: Button
    private lateinit var balance: TextView
    private lateinit var goalListView: ListView
    private val goalList: MutableList<String> = mutableListOf()
    var updatedBalance = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_main)
        var sharedPreferencesManager = SharedPreferencesManager(this)
        sharedPreferencesManager.printAllPreferences()

        inputtedBalance = findViewById(R.id.inputtedBalance)
        createGoalButton = findViewById(R.id.createGoalButton)
        updateBalanceButton = findViewById(R.id.updateBalance)
        val curr = intent
        val username = curr.getStringExtra("username")

        var balanceDouble = sharedPreferencesManager.getAccountBalance(username.toString())
        balance = findViewById(R.id.balance)
        balance.text = "$%.2f".format(balanceDouble)
        goalListView = findViewById(R.id.listView)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, goalList)
        goalListView.adapter = adapter

        createGoalButton.setOnClickListener {
            val intent = Intent(this@AccountMainActivity, NewGoal::class.java)
            createGoalLauncher.launch(intent)
        }

        updateBalanceButton.setOnClickListener {
            val currentBalance = sharedPreferencesManager.getAccountBalance(username.toString())
            val contributionAmountStr = inputtedBalance.text.toString()
            val contributionAmount = contributionAmountStr.toDoubleOrNull() ?: 0.0
            updatedBalance = currentBalance + contributionAmount
            sharedPreferencesManager.saveAccountBalance(username.toString(), updatedBalance)
            val formattedBalance = "$%.2f".format(updatedBalance)
            balance.text = formattedBalance
        }
        val logoutButton = findViewById<Button>(R.id.logout)
        logoutButton.setOnClickListener() {
            FirebaseAuth.getInstance().signOut()
            finish()
        }
        goalListView.setOnItemClickListener { _, _, position, _ ->
            val goalString = goalList[position]
            val parts = goalString.split(" ")
            val goalName = parts[0]
            val goalAmount = parts[4].removePrefix("$").toDouble().toInt()
            val progress = parts[2].removePrefix("$").toDouble().toInt()
            val intent = Intent(this@AccountMainActivity, GoalOverviewActivity::class.java).apply {
                putExtra("goalName", goalName)
                putExtra("goalAmount", goalAmount)
                putExtra("progress", progress)
                putExtra("goalIndex", position)
            }
            goalOverviewLauncher.launch(intent)
        }
    }

    private val createGoalLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // Handle the result here
            val data = result.data
            // Retrieve goal data from NewGoal activity result
            val goalName = data?.getStringExtra("goalName") ?: ""
            val goalAmount = data?.getDoubleExtra("goalAmount", 0.0) ?: 0.0
            val progress = data?.getDoubleExtra("progress", 0.0) ?: 0.0
            val dateString = data?.getStringExtra("selectedDate") ?: ""
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val date = if (dateString.isNotEmpty()) {
                val parsedDate = dateFormat.parse(dateString)
                parsedDate?.let {
                    dateFormat.format(it) // Format the date without time
                }
            } else {
                null
            }
            val goalString = "$goalName - $$progress / $$goalAmount   by $date"
            goalList.add(goalString)
            (goalListView.adapter as? ArrayAdapter<String>)?.notifyDataSetChanged()
        }
    }
    private val goalOverviewLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            var sharedPreferencesManager = SharedPreferencesManager(this)
            val curr = intent
            val username = curr.getStringExtra("username")
            val updatedProgress = data?.getIntExtra("progress", 0) ?: 0
            val goalIndex = data?.getIntExtra("goalIndex", -1) ?: -1
            val currentBal = sharedPreferencesManager.getAccountBalance(username.toString())
            updatedBalance = currentBal - updatedProgress.toDouble()
            sharedPreferencesManager.saveAccountBalance(username.toString(), updatedBalance)
            balance.setText(updatedBalance.toString())
            if (goalIndex >= 0 && goalIndex < goalList.size) {
                val goalString = goalList[goalIndex]
                val parts = goalString.split(" ")
                val goalName = parts[0]
                val goalAmount = parts[4].removePrefix("$").toDouble().toInt()
                Log.w("date", goalString)
                Log.w("date", parts[5])
                Log.w("date", parts[6])
                Log.w("date", parts[7])
                Log.w("date", parts[8])
                val newGoalString = "$goalName - $$updatedProgress / $$goalAmount            by ${parts[8]}"
                goalList[goalIndex] = newGoalString
                (goalListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
            }
        }
    }
}