package com.example.hello.service

import com.example.hello.entity.BankAccountEntity
import com.example.hello.mapper.BankAccountMapper
import com.example.hello.model.BankAccountRequest
import com.example.hello.model.BankAccountResponse
import com.example.hello.repository.BankAccountRepository
import com.example.hello.utils.JsonHelper
import mu.KotlinLogging
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import org.springframework.util.ObjectUtils
import kotlin.streams.toList


@Service
class BankAccountService {

    private val logger = KotlinLogging.logger {}
    private val mapper = Mappers.getMapper(BankAccountMapper::class.java)


    @Autowired
    lateinit var bankAccountRepository: BankAccountRepository

    fun getBankAccount(id: Long): BankAccountResponse? {
        val entity = bankAccountRepository.findByIdOrNull(id) ?: return null

        logger.info("GET BankAccount id = {} entity = {}", id, JsonHelper.objectToJson(entity))

        return mapper.convertToResponse(entity)
    }

    fun addBankAccount(bankAccountRequest: BankAccountRequest): BankAccountResponse {

        val bankAccountEntity = mapper.convertToEntity(bankAccountRequest)

        bankAccountRepository.save(bankAccountEntity)

        logger.info("ADDED BankAccount = {}", JsonHelper.objectToJson(bankAccountEntity))

        return mapper.convertToResponse(bankAccountEntity)
    }

    fun getAllBankAccount(): List<BankAccountResponse> {
        return bankAccountRepository.findAll().stream()
            .map { p -> BankAccountResponse(p.id, p.bankCode, p.accountNumber, p.accountHolderName) }
            .toList()
    }
}