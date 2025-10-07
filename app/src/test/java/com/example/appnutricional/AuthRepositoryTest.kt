package com.example.appnutricional

import com.example.appnutricional.auth.data.User
import com.example.appnutricional.auth.domain.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.junit.Assert.*
import org.mockito.kotlin.whenever

class AuthRepositoryTest {
    private val repository: UserRepository= mock()

    @Test
    fun `busqueda de usuario por correo`()= runBlocking {
        val userTest= User(0, "usuario", "prueba", "usuario@prueba.cl", "12345")
        whenever(repository.findByEmail("usuario@prueba.cl")).thenReturn(userTest)


        val result = repository.findByEmail("usuario@prueba.cl")
        assertNotNull(result)

        assertEquals("usuario@prueba.cl", result?.email)
    }

    @Test
    fun `inicio de sesión con credenciales inválidas`() = runBlocking {
        val userTest= User(0, "usuario", "prueba", "usuario@prueba.cl", "12345")

        whenever(repository.findByCredentials("user@example.com", "wrong")).thenReturn(null)

        val result = repository.findByCredentials("user@example.com", "wrong")

        assertNull(result)
    }

    @Test
    fun`creacion de usuario`()= runBlocking {
        val userTest =User(0, "usuario", "prueba", "usuario@prueba.cl", "12345")

        whenever(repository.add(userTest)).thenReturn(1)
        val result = repository.add(userTest)

        assertEquals(result, 1)
    }
}