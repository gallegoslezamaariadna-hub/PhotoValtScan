package com.photovaltscan.app.domain.model

data class Project(
    val id: String,
    val name: String,
    val client: String,
    val siteName: String,
    val address: String,
    val latitude: Double?,
    val longitude: Double?,
    val responsible: String,
    val supervisor: String,
    val assignedTechnicians: List<String>,
    val creationDate: Long,
    val scheduledDate: Long,
    val status: String,
    val type: String,
    val description: String,
    val observations: String,
    val progress: Int
)
