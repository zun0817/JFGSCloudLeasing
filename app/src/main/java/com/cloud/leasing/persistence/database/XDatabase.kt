package com.cloud.leasing.persistence.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cloud.leasing.JFGSApplication
import com.cloud.leasing.bean.User
import com.cloud.leasing.persistence.database.dao.UserDao

@Database(entities = [User::class], version = 1)
abstract class XDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private val db: XDatabase by lazy {
            Room.databaseBuilder(
                JFGSApplication.instance,
                XDatabase::class.java, "database-name"
            ).build()
        }

        fun userDao(): UserDao {
            return db.userDao()
        }
    }

}