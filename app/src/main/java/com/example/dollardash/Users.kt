package com.example.dollardash

import org.json.JSONArray
import org.json.JSONObject


data class Users (
    val username: String,
    var balance: Double,
    var goals : MutableList<NewGoal> = mutableListOf()
) {
    fun toJson(): String {
        return "$username,$balance,$goals"
    }

    fun serializeUsersList(usersList: List<Users>): String {
        val usersArray = JSONArray()
        usersList.forEach { user ->
            val userObj = JSONObject()
            userObj.put("userName", user.username)
            userObj.put("accountBalance", user.balance)

            val goalsArray = JSONArray()
            user.goals.forEach { goal ->
                val goalObj = JSONObject()
                /*goalObj.put("goalName", goal.goalName)
                goalObj.put("goalValue", goal.goalValue)*/
                goalsArray.put(goalObj)
            }

            userObj.put("goals", goalsArray)
            usersArray.put(userObj)
        }
        return usersArray.toString()
    }

}