package com.photovaltscan.app.domain.repository

import com.photovaltscan.app.domain.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getAllProjects(): Flow<List<Project>>
    suspend fun getProjectById(id: String): Project?
    suspend fun insertProject(project: Project)
    
    // Función temporal para generar datos de prueba
    suspend fun insertMockData()
}
