package com.payz.softpos.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.payz.softpos.data.local.entity.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class SoftPosDB : RoomDatabase() {

    abstract fun dao(): SoftPosDAO

    companion object {

        @Volatile
        private var INSTANCE: SoftPosDB? = null

        fun getInstance(context: Context): SoftPosDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            SoftPosDB::class.java,
            "SoftPos.db"
        ).build()
    }
}