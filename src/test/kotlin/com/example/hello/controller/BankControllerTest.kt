package com.example.hello.controller

import com.example.hello.model.BankAccountRequest
import com.example.hello.model.BankAccountResponse
import com.example.hello.service.BankAccountService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest
class BankControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var bankAccountService: BankAccountService
    private val mapper = jacksonObjectMapper()

    private val bankAccountResponse = BankAccountResponse(null,"ING", "123ING456", "JOHN SMITH")

    @Test
    fun givenExistingBankAccount_whenGetRequest_thenReturnsBankAccountJsonWithStatus200() {
        every { bankAccountService.getBankAccount(1) } returns bankAccountResponse;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/bank-account/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.bankCode").value("ING"));
    }

    @Test
    fun givenBankAccountDoesntExist_whenGetRequest_thenReturnsStatus400() {
        every { bankAccountService.getBankAccount(2) } returns null;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/bank-account/2"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    fun whenPostRequestWithBankAccountJson_thenReturnsStatus200() {
        val bankAccountRequest = BankAccountRequest("ING", "123ING456", "JOHN SMITH")
        every { bankAccountService.addBankAccount(bankAccountRequest) } returns bankAccountResponse;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bank-account").content(mapper.writeValueAsString(bankAccountRequest)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.bankCode").value("ING"));
    }
}