package com.amoukaled.androidcodechallenge.data.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amoukaled.androidcodechallenge.data.dao.UserDao
import com.amoukaled.androidcodechallenge.data.entities.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        const val DATABASE_NAME = "app_database"
    }

    abstract fun userDao(): UserDao

}