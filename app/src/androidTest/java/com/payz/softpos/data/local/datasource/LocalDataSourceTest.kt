package com.payz.softpos.data.local.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.payz.softpos.data.MainCoRoutineRule
import com.payz.softpos.data.local.database.SoftPosDB
import com.payz.softpos.data.local.entity.TransactionEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import java.util.*

@ExperimentalCoroutinesApi
class LocalDataSourceTest0 {

    //class under test
    private lateinit var localDataSource: ILocalDataSource
    private lateinit var database: SoftPosDB

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoRoutineRule = MainCoRoutineRule()

    @get:Rule
    var instantExecutorRole = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        //We use in-memory db for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SoftPosDB::class.java
        )
            .allowMainThreadQueries()
            .build()

        localDataSource = LocalDataSource(database.dao())
    }

    @After
    fun tearDown() {
        database.close()
        database.clearAllTables()
    }

    @Test
    fun save() = runBlockingTest {
        // Given an empty db and a transaction to save

        // When user saves transaction,
        localDataSource.save(fakeSaleTransactionEntity0)

        // Then there must be only one item in db
        val loaded = localDataSource.getAllTransactions()
        assertTrue(loaded.size == 1)

        // And transaction loaded by local data source should be equal to teh saved one.
        assertEquals(fakeSaleTransactionEntity0.transactionUUID, loaded[0].transactionUUID)
        assertEquals(fakeSaleTransactionEntity0.transactionAmount, loaded[0].transactionAmount, 0.0)
        assertEquals(fakeSaleTransactionEntity0.transactionName, loaded[0].transactionName)
        assertEquals(fakeSaleTransactionEntity0.transactionTime, loaded[0].transactionTime)
    }

    @Test
    fun getRefundableTransactions() = runBlockingTest {
        // Given an empty db and a transaction to save

        // When user saves transaction,
        localDataSource.save(fakeSaleTransactionEntity0)

        // Then there must be only one item in db
        val loaded = localDataSource.getRefundableTransactions()
        assertTrue(loaded.size == 1)

        // And transaction loaded by local data source should be equal to teh saved one.
        assertEquals(fakeSaleTransactionEntity0.transactionUUID, loaded[0].transactionUUID)
        assertEquals(fakeSaleTransactionEntity0.transactionAmount, loaded[0].transactionAmount, 0.0)
        assertEquals(fakeSaleTransactionEntity0.transactionName, loaded[0].transactionName)
        assertEquals(fakeSaleTransactionEntity0.transactionTime, loaded[0].transactionTime)
    }

    @Test
    fun getAllTransactions() = runBlockingTest {
        // Given an empty db and a transaction to save

        // When user saves two transactions,
        localDataSource.save(fakeSaleTransactionEntity0)
        localDataSource.save(fakeSaleTransactionEntity1)

        // Then there must be only two items in db
        val loaded = localDataSource.getAllTransactions()
        assertEquals(2, loaded.size)

        // And the first transaction loaded by local data source should be equal to the first saved one.
        assertEquals(fakeSaleTransactionEntity0.transactionUUID, loaded[0].transactionUUID)
        assertEquals(fakeSaleTransactionEntity0.transactionAmount, loaded[0].transactionAmount, 0.0)
        assertEquals(fakeSaleTransactionEntity0.transactionName, loaded[0].transactionName)
        assertEquals(fakeSaleTransactionEntity0.transactionTime, loaded[0].transactionTime)

        // And the second transaction loaded by local data source should be equal to the secondly saved one.
        assertEquals(fakeSaleTransactionEntity1.transactionUUID, loaded[1].transactionUUID)
        assertEquals(fakeSaleTransactionEntity1.transactionAmount, loaded[1].transactionAmount, 0.0)
        assertEquals(fakeSaleTransactionEntity1.transactionName, loaded[1].transactionName)
        assertEquals(fakeSaleTransactionEntity1.transactionTime, loaded[1].transactionTime)
    }
}


val fakeSaleTransactionEntity0 = TransactionEntity(
    transactionUUID = UUID.randomUUID().toString(),
    transactionName = "SALE",
    transactionTime = Date().toString(),
    transactionAmount = 0.0,
    currencyCode = "AZN"
)

val fakeSaleTransactionEntity1 = TransactionEntity(
    transactionUUID = UUID.randomUUID().toString(),
    transactionName = "SALE",
    transactionTime = Date().toString(),
    transactionAmount = 0.0,
    currencyCode = "AZN"
)
