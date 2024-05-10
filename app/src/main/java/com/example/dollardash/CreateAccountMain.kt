package com.example.dollardash

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccountMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        var sharedPreferencesManager = SharedPreferencesManager(this)

        var firebase : FirebaseDatabase = FirebaseDatabase.getInstance( )
        var reference : DatabaseReference = firebase.getReference()
        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        val backToLoginButton = findViewById<Button>(R.id.backToLoginButton)
        val createAccountButton = findViewById<Button>(R.id.createAccountButton)
        //currently login button does not work when you "go back to login button"
        backToLoginButton.setOnClickListener() {
            finish()
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
                        sharedPreferencesManager.saveAccountBalance(inputEmail.text.toString(), 0.0)
                        Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_LONG).show()
                    } else {
                        Log.w("Registration", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}