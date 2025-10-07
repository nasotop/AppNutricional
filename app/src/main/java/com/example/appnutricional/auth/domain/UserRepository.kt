package com.example.appnutricional.auth.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appnutricional.auth.data.User
@Dao
interface UserRepository {
     @Query("Select * from user")
     fun getAll(): List<User>
     @Query("Select * from user where email = :email limit 1")
     fun findByEmail(email: String): User?
     @Query("Select * from user where email = :email and password = :password limit 1")

     fun findByCredentials(email: String, password: String): User?
     @Insert(onConflict = OnConflictStrategy.ABORT)
     fun add(user: User): Long
}