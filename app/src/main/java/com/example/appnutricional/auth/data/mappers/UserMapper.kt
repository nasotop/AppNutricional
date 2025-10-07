package com.example.appnutricional.auth.data.mappers

import com.example.appnutricional.auth.data.User
import com.example.appnutricional.core.domain.UserModel

object UserMapper {
    fun toModel(user: User): UserModel =
        UserModel(user.names, user.lastNames, user.email, user.password)

    fun toEntity(user: UserModel): User = User(
        names = user.names, lastNames = user.lastNames, email = user.email, password = user.password
    )
}