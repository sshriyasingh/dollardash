package com.example.myapp

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

// Data class for Goals
data class Goal(
    val goalName: String,
    val totalContribution: Double,
    val currentContribution: Double
)

// Data class for User
data class User(
    val userName: String,
    val goals: List<Goal>,
    val accountBalance: Double
)

class UserDataManager(private val context: Context) {
    private val sharedPreferencesName = "UserPrefs"

    // Function to serialize a list of User objects to a JSON string
    fun serializeUsersList(usersList: List<User>): String {
        val usersArray = JSONArray()
        usersList.forEach { user ->
            val userObj = JSONObject()
            userObj.put("userName", user.userName)
            userObj.put("accountBalance", user.accountBalance)

            val goalsArray = JSONArray()
            user.goals.forEach { goal ->
                val goalObj = JSONObject()
                goalObj.put("goalName", goal.goalName)
                goalObj.put("totalContribution", goal.totalContribution)
                goalObj.put("currentContribution", goal.currentContribution)
                goalsArray.put(goalObj)
            }

            userObj.put("goals", goalsArray)
            usersArray.put(userObj)
        }
        return usersArray.toString()
    }

    // Function to deserialize a JSON string to a list of User objects
    fun deserializeUsersList(jsonString: String): List<User> {
        val usersList = mutableListOf<User>()
        val usersArray = JSONArray(jsonString)
        for (i in 0 until usersArray.length()) {
            val userObj = usersArray.getJSONObject(i)
            val goalsList = mutableListOf<Goal>()
            val goalsArray = userObj.getJSONArray("goals")
            for (j in 0 until goalsArray.length()) {
                val goalObj = goalsArray.getJSONObject(j)
                goalsList.add(Goal(
                    goalObj.getString("goalName"),
                    goalObj.getDouble("totalContribution"),
                    goalObj.getDouble("currentContribution")
                ))
            }
            usersList.add(User(userObj.getString("userName"), goalsList, userObj.getDouble("accountBalance")))
        }
        return usersList
    }

    // Function to save user data to SharedPreferences
    fun saveUsersToPreferences(usersList: List<User>) {
        val prefs = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = serializeUsersList(usersList)
        editor.putString("users", json)
        editor.apply()
    }

    // Function to load user data from SharedPreferences
    fun loadUsersFromPreferences(): List<User> {
        val prefs = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val json = prefs.getString("users", "[]") ?: "[]"
        return deserializeUsersList(json)
    }

    fun addGoalToUser(username: String, newGoal: Goal) {
        val users = loadUsersFromPreferences() // Load current users
        val updatedUsers = users.map { user ->
            if (user.userName == username) {
                // Create a new list of goals with the new goal added
                val updatedGoals = user.goals.toMutableList().apply { add(newGoal) }
                user.copy(goals = updatedGoals) // Return updated user
            } else {
                user // Return user as is if not the one we're looking for
            }
        }
        saveUsersToPreferences(updatedUsers) // Save the updated list back to SharedPreferences
    }
}
