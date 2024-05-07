package com.example.dollardash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val createAccountButtonMain = findViewById<Button>(R.id.createAccountButtonMain)

        var adLayout : LinearLayout = findViewById<LinearLayout>(R.id.ad_view)
        var adView : AdView = AdView( this )
        adView.setAdSize(AdSize( AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT))
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        var builder : AdRequest.Builder = AdRequest.Builder()
        var request : AdRequest = builder.build()
        adLayout.addView( adView )
        adView.loadAd( request )

        loginButton.setOnClickListener() {
            setContentView(R.layout.activity_account_main)
            val datePicker = findViewById<DatePicker>(R.id.datePicker)
            val calendar = Calendar.getInstance()
            datePicker.minDate = calendar.timeInMillis
            val inputtedBalance = findViewById<EditText>(R.id.inputtedBalance)
            val updateBalance = findViewById<Button>(R.id.updateBalance)
            var balance = 0
            val currBalance = findViewById<TextView>(R.id.currBalance)
            updateBalance.setOnClickListener() {
                val inputStr = inputtedBalance.text.toString()
                if (inputStr.isNotEmpty()) {
                    val number = inputStr.toInt()
                    balance += number
                    currBalance.text = "Your Current Balance: \$" + balance
                }
            }
        }

        createAccountButtonMain.setOnClickListener() {
            setContentView(R.layout.activity_create_account)
            val backToLoginButton = findViewById<Button>(R.id.backToLoginButton)
            //currently login button does not work when you "go back to login button"
            backToLoginButton.setOnClickListener() {
                setContentView(R.layout.activity_main)
            }
        }
    }
}