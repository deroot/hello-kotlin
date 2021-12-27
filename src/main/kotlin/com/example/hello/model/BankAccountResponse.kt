package com.example.hello.model

data class BankAccountResponse(
        var id: Long? = null,
        var bankCode: String? = null,
        var accountNumber: String? = null,
        var accountHolderName: String? = null
)
