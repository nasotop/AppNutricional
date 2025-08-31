package com.example.appnutricional.auth.domain

import com.example.appnutricional.core.domain.UserModel

interface UserRepository {
     fun getAll(): List<UserModel>
     fun findByEmail(email: String): UserModel?
     fun findByCredentials(email: String, password: String): UserModel?
     fun add(user: UserModel): Boolean
}