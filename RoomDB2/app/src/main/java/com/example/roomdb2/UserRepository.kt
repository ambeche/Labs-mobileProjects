package com.example.roomdb2

import com.example.roomdb2.Car
import com.example.roomdb2.CarDao
import com.example.roomdb2.User
import com.example.roomdb2.UserDao

class UserRepository(private val userDao: UserDao, private val carDao: CarDao) {
    val userData = userDao.getUsersAndCars()

     suspend fun insertUser(user: User) = userDao.insert(user)

     suspend fun insertCar(car: Car) = carDao.insert(car)
}