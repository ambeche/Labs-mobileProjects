package com.example.roomdb2

import android.app.Application
import androidx.lifecycle.*
import com.example.roomdb2.*
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository(
        UserDB.get(application).userDao(), UserDB.get(application).carDao() )

    val userData: LiveData<List<UserWithCars>> = repository.userData

    fun getCarOwnerId(lastName: String):Long? {
        return userData.value?.map { u -> u.user }
           ?.find { user -> user.lastName == lastName }?.userId
    }


    var userId: Long? = null

    fun addUser(user: User) {
       viewModelScope.launch(Dispatchers.IO) {
           val id = repository.insertUser(user)
           withContext(Dispatchers.Main){
             userId = id
           }
        }
    }

    fun addCar(car: Car) {
       viewModelScope.launch(Dispatchers.IO) { repository.insertCar(car) }
    }

}