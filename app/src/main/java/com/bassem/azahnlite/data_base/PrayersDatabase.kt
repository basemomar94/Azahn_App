package com.bassem.azahnlite.data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bassem.azahnlite.api.Item
import java.util.concurrent.Executors

const val DATABASE_NAME = "Prayers_Database"

@Database(entities = [Item::class], version = 1)
abstract class PrayersDatabase : RoomDatabase() {

    abstract fun dao(): Dao

    companion object {
        private var INSTANCE: PrayersDatabase? = null
        val db_write = Executors.newFixedThreadPool(4)
        fun getinstance(context: Context?): PrayersDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            kotlin.synchronized(this) {
                val instance = Room.databaseBuilder(
                    context!!.applicationContext, PrayersDatabase::class.java, DATABASE_NAME

                ).build()
                INSTANCE = instance
                return instance
            }
        }


    }

}