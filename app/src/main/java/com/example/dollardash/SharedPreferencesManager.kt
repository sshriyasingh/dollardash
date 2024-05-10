package com.example.dollardash

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    /**
     * Saves the account balance for a given username.
     *
     * @param username The key under which the account balance will be stored.
     * @param balance The account balance to be stored.
     */
    fun saveAccountBalance(username: String, balance: Double) {
        val editor = sharedPreferences.edit()
        editor.putFloat(username, balance.toFloat())  // Convert double to float for SharedPreferences
        editor.apply()
    }

    /**
     * Retrieves the account balance for a given username.
     *
     * @param username The key under which the account balance is stored.
     * @return The retrieved account balance, or 0.0 if not found.
     */
    fun getAccountBalance(username: String): Double {
        return sharedPreferences.getFloat(username, 0.0f).toDouble()  // Convert float back to double
    }

    fun printAllPreferences() {
        val allEntries = sharedPreferences.all
        for ((key, value) in allEntries) {
            Log.d("SharedPreferences", "Key: $key, Value: $value")
        }
    }
}
