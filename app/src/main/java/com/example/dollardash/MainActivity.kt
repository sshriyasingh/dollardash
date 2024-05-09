package com.example.dollardash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var firebase : FirebaseDatabase = FirebaseDatabase.getInstance( )
        var reference : DatabaseReference = firebase.getReference()
        val auth: FirebaseAuth = FirebaseAuth.getInstance()

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
            val loginEmail = findViewById<EditText>(R.id.username)
            val loginPassword = findViewById<EditText>(R.id.password)
            auth.signInWithEmailAndPassword(loginEmail.text.toString(), loginPassword.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, AccountMainActivity::class.java)
                        //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                        startActivity(intent)
                        /*val datePicker = findViewById<DatePicker>(R.id.datePicker)
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
                            setContentView(R.layout.activity_goal_overview)
                            //currently login button does not work when you "go back to login button"
                        }*/
                    } else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                    }
                }
        }

        createAccountButtonMain.setOnClickListener() {
            val intent = Intent(this, CreateAccountMain::class.java)
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            startActivity(intent)
            /*val logoutButton = findViewById<Button>(R.id.logout)
            logoutButton.setOnClickListener() {
                FirebaseAuth.getInstance().signOut()
            }*/
            /*val backToLoginButton = findViewById<Button>(R.id.backToLoginButton)
            val createAccountButton = findViewById<Button>(R.id.createAccountButton)
            //currently login button does not work when you "go back to login button"
            backToLoginButton.setOnClickListener() {
                setContentView(R.layout.activity_main)
            }
            createAccountButton.setOnClickListener() {
                val inputEmail = findViewById<EditText>(R.id.createusername)
                val inputPassword = findViewById<EditText>(R.id.createpassword)
                if (inputPassword.text.toString().length < 6) {
                    Toast.makeText(this, "Make sure password length is 6+ characters", Toast.LENGTH_LONG).show()
                }
                auth.createUserWithEmailAndPassword(inputEmail.text.toString(), inputPassword.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_LONG).show()
                        } else {
                            Log.w("Registration", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_LONG).show()
                        }
                    }
            }*/
        }
    }
}