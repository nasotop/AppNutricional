package com.example.appnutricional.core.domain

class DuplicateUserException(email: String) :
    IllegalStateException("El correo ya se encuentra registrado: $email")
