package com.example.dollardash

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    fun saveAccountBalance(username: String, balance: Double) {
        val editor = sharedPreferences.edit()
        editor.putFloat(username, balance.toFloat())
        editor.apply()
    }

    fun getAccountBalance(username: String): Double {
        return sharedPreferences.getFloat(username, 0.0f).toDouble()
    }

    fun printAllPreferences() {
        val allEntries = sharedPreferences.all
        for ((key, value) in allEntries) {
            Log.d("SharedPreferences", "Key: $key, Value: $value")
        }
    }
}
