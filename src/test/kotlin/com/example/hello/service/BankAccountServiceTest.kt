package com.example.hello.service

import com.example.hello.entity.BankAccountEntity
import com.example.hello.mapper.BankAccountMapper
import com.example.hello.model.BankAccountRequest
import com.example.hello.repository.BankAccountRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import mu.KotlinLogging
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mapstruct.factory.Mappers
import org.springframework.data.repository.findByIdOrNull

@ExtendWith(MockKExtension::class)
internal class BankAccountServiceTest {
    private val logger = KotlinLogging.logger {}
    private val mapper = Mappers.getMapper(BankAccountMapper::class.java)

    @InjectMockKs
    lateinit var bankAccountService: BankAccountService

    @MockK
    lateinit var bankAccountRepository: BankAccountRepository

    @Test
    fun getBankAccount() {

        //given
        var bankAccountEntity = BankAccountEntity(null, "ING", "123ING456", "JOHN SMITH")
        every { bankAccountRepository.findByIdOrNull(1) } returns bankAccountEntity

        //when
        val result = bankAccountService.getBankAccount(1);

        //then
        verify(exactly = 1) { bankAccountRepository.findByIdOrNull(1) }

        val expected = mapper.convertToResponse(bankAccountEntity)
        assertEquals(expected, result)
    }

    @Test
    fun addBankAccount() {

        //captor
        val bankAccountEntitySlot = slot<BankAccountEntity>()

        //given
        every { bankAccountRepository.save(capture(bankAccountEntitySlot)) } returns BankAccountEntity(null, "ING", "123ING456", "JOHN SMITH")

        //when
        var bankAccountRequest = BankAccountRequest("ING", "123ING456", "JOHN SMITH")
        val addedBankAccount = bankAccountService.addBankAccount(bankAccountRequest)

        //then
        verify { bankAccountRepository.save(any()) }

        logger.info("saved bankAccountEntity = {}", bankAccountEntitySlot.captured)

        assertEquals("ING", bankAccountEntitySlot.captured.bankCode)
        assertEquals("123ING456", bankAccountEntitySlot.captured.accountNumber)
        assertEquals("JOHN SMITH", bankAccountEntitySlot.captured.accountHolderName)
    }
}