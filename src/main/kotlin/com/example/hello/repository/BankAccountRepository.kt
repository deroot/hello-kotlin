package com.example.hello.repository

import com.example.hello.entity.BankAccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BankAccountRepository : JpaRepository<BankAccountEntity, Long> {
}