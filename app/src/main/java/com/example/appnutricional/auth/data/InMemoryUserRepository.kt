package com.example.appnutricional.auth.data

import com.example.appnutricional.auth.domain.UserRepository
import com.example.appnutricional.core.domain.UserModel

class InMemoryUserRepository {
    private val users = mutableListOf(
        UserModel("Juan", "Pérez", "juan@example.com", "12345678"),
        UserModel("Ana", "López", "ana@example.com", "abcdef12"),
        UserModel("Carlos", "García", "carlos@example.com", "passw0rd"),
        UserModel("Lucía", "Martínez", "lucia@example.com", "qwertyui"),
        UserModel("Pedro", "Sánchez", "pedro@example.com", "zxcvbnm1"),
    )

     fun getAll(): List<UserModel> {
        return users.toList()
    }

     fun findByEmail(email: String): UserModel? {
        return users.find { it.email.equals(email, true) }
    }

     fun findByCredentials(
        email: String,
        password: String
    ): UserModel? {
        return users.find {
            it.email.equals(email, true) &&
            it.password == password
        }

    }

     fun add(user: UserModel): Boolean {
        if (users.any { it.email.equals(user.email, true) })
            return false

        users.add(user)
        return true
    }
}