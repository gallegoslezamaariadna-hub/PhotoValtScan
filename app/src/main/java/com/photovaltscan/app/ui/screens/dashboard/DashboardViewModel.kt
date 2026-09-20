package com.photovaltscan.app.ui.screens.dashboard

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photovaltscan.app.domain.model.Project
import com.photovaltscan.app.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: ProjectRepository,
    private val prefs: SharedPreferences
) : ViewModel() {

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    val employeeId = prefs.getString("current_user", "TEC-402") ?: "TEC-402"
    val userName = prefs.getString("name_$employeeId", "Usuario") ?: "Usuario"
    val userRole = prefs.getString("role_$employeeId", "Técnico") ?: "Técnico"

    init {
        // Insertamos datos de prueba y luego escuchamos la base de datos
        viewModelScope.launch {
            repository.insertMockData()
            repository.getAllProjects().collect { projectList ->
                _projects.value = projectList
            }
        }
    }
}
