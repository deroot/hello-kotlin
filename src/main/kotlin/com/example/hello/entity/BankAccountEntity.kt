package com.example.hello.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class BankAccountEntity(
        @Id @GeneratedValue
        var id: Long? = null,
        var bankCode: String,
        var accountNumber: String,
        var accountHolderName: String
)
