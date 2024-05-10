package com.example.dollardash
class Goal(goalName: String, totalContribution: Int, currentContribution: Int) {
    private val name = goalName
    private val totalContrib = totalContribution
    private var currentContrib = currentContribution
    fun getName(): String {
        return name
    }
    fun getCurrContrib(): Int {
        return currentContrib
    }
    fun getTotal(): Int {
        return totalContrib
    }
    fun setCurrContrib(input: Int): Unit {
        currentContrib = input
    }
}


