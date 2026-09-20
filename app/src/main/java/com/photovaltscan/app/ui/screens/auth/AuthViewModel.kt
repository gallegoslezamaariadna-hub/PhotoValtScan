package com.photovaltscan.app.ui.screens.auth

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val prefs: SharedPreferences
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun register(fullName: String, employeeId: String, email: String, role: String, pass: String) {
        if (fullName.isBlank() || employeeId.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("Por favor completa los campos requeridos.")
            return
        }
        
        // Simulación: Guardamos en SharedPreferences usando el employeeId como clave base
        prefs.edit().apply {
            putString("user_$employeeId", pass)
            putString("name_$employeeId", fullName)
            putString("email_$employeeId", email)
            putString("role_$employeeId", role)
            if (role == "Administrador Central") {
                putString("status_$employeeId", "approved")
            } else {
                putString("status_$employeeId", "pending")
            }
            apply()
        }
        
        _authState.value = AuthState.RegisterSuccess
    }

    fun login(employeeId: String, pass: String, selectedTabIndex: Int) {
        if (employeeId.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("Ingresa usuario y contraseña.")
            return
        }

        val savedPass = prefs.getString("user_$employeeId", null)
        val savedRole = prefs.getString("role_$employeeId", null)
        val status = prefs.getString("status_$employeeId", "approved")
        
        if (savedPass != null && savedPass == pass) {
            if (status == "pending") {
                _authState.value = AuthState.Error("Tu cuenta está pendiente de asignación de rol por un Administrador.")
                return
            }

            // Validamos que el tab seleccionado coincida con el rol guardado
            val isRoleValid = when (selectedTabIndex) {
                0 -> savedRole != "Administrador Central" && savedRole != "Soporte Operativo"
                1 -> savedRole == "Administrador Central"
                2 -> savedRole == "Soporte Operativo"
                else -> false
            }

            if (isRoleValid) {
                prefs.edit().putString("current_user", employeeId).apply()
                if (selectedTabIndex == 1) {
                    _authState.value = AuthState.LoginSuccessAdmin
                } else {
                    _authState.value = AuthState.LoginSuccess
                }
            } else {
                _authState.value = AuthState.Error("Acceso denegado: Tu perfil no corresponde a la pestaña seleccionada.")
            }
        } else {
            _authState.value = AuthState.Error("Usuario o contraseña incorrectos.")
        }
    }
    
    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object LoginSuccess : AuthState()
    object LoginSuccessAdmin : AuthState()
    object RegisterSuccess : AuthState()
    data class Error(val message: String) : AuthState()
}