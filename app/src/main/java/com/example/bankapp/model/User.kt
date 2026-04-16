package com.example.bankapp.model

data class User(
    val username: String,
    val password: String,
    val fullName: String,
    val balance: Double
)