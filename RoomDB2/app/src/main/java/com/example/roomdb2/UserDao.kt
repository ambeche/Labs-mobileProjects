package com.example.roomdb2

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
 interface UserDao {
    @Transaction
    @Query("SELECT * FROM users")
    fun getUsersAndCars(): LiveData<List<UserWithCars>>

    @Query("SELECT * FROM users")
    fun getAllUsers() : List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long
 }

// Car D.A.O
@Dao
interface CarDao {
    @Query("SELECT * FROM cars")
    fun getCars() : List<Car>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(car: Car): Long
}
