package com.example.bankapp.model

data class Transaction(
    val id: Int,
    val date: String,
    val amount: Double,
    val status: String,
    val description: String
)