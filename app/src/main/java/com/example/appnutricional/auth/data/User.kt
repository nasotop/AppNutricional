package com.example.appnutricional.auth.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="user")
data class User(
@PrimaryKey(autoGenerate = true) val id: Int=0,
val names: String,
val lastNames: String,
val email: String,
val password: String
)
