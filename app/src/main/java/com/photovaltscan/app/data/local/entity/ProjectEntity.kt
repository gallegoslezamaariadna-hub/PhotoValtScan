package com.photovaltscan.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey val id: String,
    val name: String,
    val client: String,
    val siteName: String,
    val address: String,
    val latitude: Double?,
    val longitude: Double?,
    val responsible: String,
    val supervisor: String,
    // Note: In Room, Lists require TypeConverters. We store as JSON string for simplicity.
    val assignedTechniciansJson: String,
    val creationDate: Long,
    val scheduledDate: Long,
    val status: String,
    val type: String,
    val description: String,
    val observations: String,
    val progress: Int
)
