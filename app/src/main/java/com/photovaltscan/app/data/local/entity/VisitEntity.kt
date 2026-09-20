package com.photovaltscan.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visits")
data class VisitEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val date: Long,
    val startTime: Long,
    val endTime: Long?,
    val technician: String,
    val supervisor: String,
    val initialLocationLat: Double?,
    val initialLocationLng: Double?,
    val finalLocationLat: Double?,
    val finalLocationLng: Double?,
    val visitReason: String,
    val surveyType: String,
    val status: String,
    val observations: String
)
