package com.example.roomdb2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(User::class), (Car::class)],
    version = 1)
abstract class UserDB: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun carDao(): CarDao

    companion object {

        var dbInstance: UserDB? = null
        @Synchronized
        fun get(context: Context): UserDB {
            if (dbInstance == null) {
                dbInstance =
                    Room.databaseBuilder(context.applicationContext,
                        UserDB::class.java, "user.db").build()
            }
            return dbInstance!!
        }
    }
}