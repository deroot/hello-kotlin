package com.example.hello.mapper

import com.example.hello.entity.BankAccountEntity
import com.example.hello.model.BankAccountRequest
import com.example.hello.model.BankAccountResponse
import mu.KotlinLogging
import org.mapstruct.*

@Mapper
abstract class BankAccountMapper {

    private val logger = KotlinLogging.logger {}

    @BeanMapping(qualifiedByName = ["xxx"])
    @Mapping(source = "bankCode", target = "bankCode")
    abstract fun convertToResponse(bankAccountEntity: BankAccountEntity): BankAccountResponse

    @BeanMapping(qualifiedByName = ["yyy"])
    @Mapping(source = "bankCode", target = "bankCode")
    abstract fun convertToEntity(bankAccountRequest: BankAccountRequest): BankAccountEntity

    @Named("xxx")
    @AfterMapping
    protected fun afterConvertToResponse(
        @MappingTarget target: BankAccountResponse,
        bankAccountEntity: BankAccountEntity
    ) {
        // do something
        logger.info("AfterMapping xxx")
    }

    @Named("yyy")
    @AfterMapping
    protected fun afterConvertToEntity(
        @MappingTarget target: BankAccountEntity,
        bankAccountRequest: BankAccountRequest
    ) {
        // do something
        logger.info("AfterMapping yyy")
    }
}