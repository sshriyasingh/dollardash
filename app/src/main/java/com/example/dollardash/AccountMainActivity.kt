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
            val inputStr = inputtedBalance.text.toString()
            if (inputStr.isNotEmpty()) {
                balanceDouble += inputStr.toDouble()
                sharedPreferencesManager.saveAccountBalance(username.toString(), balanceDouble)
                val formattedBalance = "$%.2f".format(balanceDouble)
                balance.text = formattedBalance
            }
        }
        val logoutButton = findViewById<Button>(R.id.logout)
        logoutButton.setOnClickListener() {
            FirebaseAuth.getInstance().signOut()
            finish()
        }
        // Inside onCreate method of MainActivity
        goalListView.setOnItemClickListener { parent, view, position, id ->
            val goalString = goalList[position]
            Log.w("goalString", goalString)
            val parts = goalString.split(" ")
            Log.w("parts", parts[0])
            Log.w("parts", parts[1])
            Log.w("parts", parts[2])
            Log.w("parts", parts[3])
            Log.w("parts", parts[4])

            val goalName = parts[0] // Extracting the goalName
            val goalAmount = parts[4].removePrefix("$").toDouble().toInt() // Extracting the goalAmount
            val progress = parts[2].removePrefix("$").toDouble().toInt()
            val date = parts[2].substring(4) // Extracting the date (excluding "by ")
            val intent2 = Intent(this@AccountMainActivity, GoalOverviewActivity::class.java).apply {
                putExtra("goalName", goalName)
                Log.w("goalAmount", goalAmount.toString())
                putExtra("goalAmount", goalAmount)
                putExtra("date", date)
                putExtra("progress", progress)
                putExtra("username", username)
            }
            startActivity(intent2)
        }
    }

    private val createGoalLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
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
            val goalString = "$goalName - $$progress / $$goalAmount                         by $date"
            goalList.add(goalString)
            (goalListView.adapter as? ArrayAdapter<String>)?.notifyDataSetChanged()
        }
    }

}
