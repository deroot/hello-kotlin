package com.example.hello.model

data class BankAccountRequest(
        var bankCode: String,
        var accountNumber: String,
        var accountHolderName: String
)
