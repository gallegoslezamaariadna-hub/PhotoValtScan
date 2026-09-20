package com.photovaltscan.app.data.repository

import com.photovaltscan.app.data.local.dao.ProjectDao
import com.photovaltscan.app.data.local.entity.ProjectEntity
import com.photovaltscan.app.domain.model.Project
import com.photovaltscan.app.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val projectDao: ProjectDao
) : ProjectRepository {

    override fun getAllProjects(): Flow<List<Project>> {
        return projectDao.getAllProjects().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getProjectById(id: String): Project? {
        return projectDao.getProjectById(id)?.toDomain()
    }

    override suspend fun insertProject(project: Project) {
        projectDao.insertProject(project.toEntity())
    }

    override suspend fun insertMockData() {
        val mocks = listOf(
            Project(
                id = "PRJ-001",
                name = "TRUPER Monterrey",
                client = "TRUPER",
                siteName = "CEDIS Monterrey",
                address = "Parque Industrial Monterrey",
                latitude = 25.6866,
                longitude = -100.3161,
                responsible = "Juan Pérez",
                supervisor = "Ana Gómez",
                assignedTechnicians = listOf("Carlos López"),
                creationDate = System.currentTimeMillis(),
                scheduledDate = System.currentTimeMillis() + 86400000,
                status = "En revisión",
                type = "Viabilidad FV",
                description = "Levantamiento eléctrico preliminar para sistema de 500kWp",
                observations = "Requiere permiso especial de acceso",
                progress = 86
            ),
            Project(
                id = "PRJ-002",
                name = "Bimbo Guadalajara",
                client = "Grupo Bimbo",
                siteName = "Planta Occidente",
                address = "Zona Industrial Guadalajara",
                latitude = 20.6596,
                longitude = -103.3496,
                responsible = "Luis Torres",
                supervisor = "Ana Gómez",
                assignedTechnicians = listOf("Carlos López"),
                creationDate = System.currentTimeMillis() - 86400000,
                scheduledDate = System.currentTimeMillis() + 172800000,
                status = "Asignado",
                type = "Mantenimiento FV",
                description = "Mantenimiento preventivo e inspección termográfica",
                observations = "Llevar cámara FLIR",
                progress = 0
            )
        )
        mocks.forEach { insertProject(it) }
    }

    // Mappers
    private fun ProjectEntity.toDomain(): Project {
        return Project(
            id = id,
            name = name,
            client = client,
            siteName = siteName,
            address = address,
            latitude = latitude,
            longitude = longitude,
            responsible = responsible,
            supervisor = supervisor,
            assignedTechnicians = emptyList(), // Omitido por simplicidad en esta versión
            creationDate = creationDate,
            scheduledDate = scheduledDate,
            status = status,
            type = type,
            description = description,
            observations = observations,
            progress = progress
        )
    }

    private fun Project.toEntity(): ProjectEntity {
        return ProjectEntity(
            id = id,
            name = name,
            client = client,
            siteName = siteName,
            address = address,
            latitude = latitude,
            longitude = longitude,
            responsible = responsible,
            supervisor = supervisor,
            assignedTechniciansJson = "[]",
            creationDate = creationDate,
            scheduledDate = scheduledDate,
            status = status,
            type = type,
            description = description,
            observations = observations,
            progress = progress
        )
    }
}
