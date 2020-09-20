package com.example.roomdb2

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true) val userId: Long,
    val firstName: String,
    val lastName: String,
    @Embedded val address: UserAddress
) {
    override fun toString(): String = "$firstName $lastName"
}

data class UserAddress (
    val house: String,
    val street: String,
    val city: String,
    val postal: Long
){
    override fun toString(): String {
        return "$street $house\n$postal $city"
    }
}

@Entity(tableName = "cars")
data class Car (
    @PrimaryKey(autoGenerate = true) val carId: Long,
    val owner: Long,
    val make: String,
    val model: String,
    val engine: String,
    val color: String,
    val carValue: Int
) {
    override fun toString(): String =
        "$color $make $model with engine $engine, valued at $carValue"
}

// class defining one-to-many relationship between user and car
data class UserWithCars(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "owner"
    )
    val cars: List<Car>
)

