package com.payz.softpos.data.repository

import com.payz.softpos.data.local.datasource.FakeLocalDataSource
import com.payz.softpos.data.local.datasource.ILocalDataSource
import com.payz.softpos.data.local.entity.TransactionEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class RepositoryTest {

    //class under test
    private lateinit var repository: IRepository

    private lateinit var localDataSource: ILocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        repository = Repository(localDataSource)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun save() = runBlockingTest {
        // Given that we have an empty repository
        assertTrue(repository.getAllTransactions().isEmpty())

        // when user saves a transaction,
        repository.save(fakeSaleTransactionEntity)

        // Then repository is supposed to return only one transaction
        assertTrue(repository.getAllTransactions().size == 1)
    }

    @Test
    fun getRefundableTransactions() = runBlockingTest {
        // Given that we have an empty repository
        assertTrue(repository.getRefundableTransactions().isEmpty())

        // when user saves a transaction,
        repository.save(fakeSaleTransactionEntity)

        // Then repository is supposed to return only one transaction
        assertTrue(repository.getRefundableTransactions().size == 1)
    }

    @Test
    fun getAllTransactions() = runBlockingTest {
        // Given that we have an empty repository
        assertTrue(repository.getAllTransactions().isEmpty())

        // when user saves a transaction,
        repository.save(fakeSaleTransactionEntity)

        // Then repository is supposed to return only one transaction
        assertTrue(repository.getAllTransactions().size == 1)
    }
}

val fakeSaleTransactionEntity = TransactionEntity(
    transactionUUID = UUID.randomUUID().toString(),
    transactionName = "SALE",
    transactionTime = Date().toString(),
    transactionAmount = 0.0,
    currencyCode = "AZN"
)