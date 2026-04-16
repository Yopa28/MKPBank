package com.example.bankapp.data.dummy

import com.example.bankapp.model.Transaction
import com.example.bankapp.model.User

object DummyData {

    // user login
    val dummyUser = User(
        username = "yopa28",
        password = "yopa12328",
        fullName = "Sandy Yopa",
        balance = 15750000.0
    )

    // List Transaction
    val transactions = listOf(
        Transaction(
            id = 1,
            date = "14 Apr 2026",
            amount = 500000.0,
            status = "Success",
            description = "Transfer to 1234567890"
        ),
        Transaction(
            id = 2,
            date = "13 Apr 2026",
            amount = 250000.0,
            status = "Failed",
            description = "Transfer to 0987654321"
        ),
        Transaction(
            id = 3,
            date = "12 Apr 2026",
            amount = 1000000.0,
            status = "Success",
            description = "Transfer to 1122334455"
        ),
        Transaction(
            id = 4,
            date = "11 Apr 2026",
            amount = 750000.0,
            status = "Success",
            description = "Transfer to 5544332211"
        ),
        Transaction(
            id = 5,
            date = "10 Apr 2026",
            amount = 300000.0,
            status = "Failed",
            description = "Transfer to 9988776655"
        )
    )
}