package com.example.hello.controller

import com.example.hello.model.BankAccountRequest
import com.example.hello.model.BankAccountResponse
import com.example.hello.service.BankAccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/bank-account")
@RestController
class BankController {

    @Autowired
    lateinit var bankAccountService: BankAccountService

    @PostMapping
    fun addBankAccount(@RequestBody bankAccountRequest: BankAccountRequest): BankAccountResponse {
        return bankAccountService.addBankAccount(bankAccountRequest)
    }

    @GetMapping
    fun getAllBankAccount(): List<BankAccountResponse> {
        return bankAccountService.getAllBankAccount()
    }


    @GetMapping("/{id}")
    fun getBankAccount(@PathVariable("id") id: Long): ResponseEntity<BankAccountResponse> {
        val bankAccountResponse = bankAccountService.getBankAccount(id) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(bankAccountResponse)
    }
}