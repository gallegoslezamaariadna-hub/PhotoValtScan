package com.photovaltscan.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media_files")
data class MediaEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val sectionId: String,
    val mediaType: String, // "IMAGE" or "VIDEO"
    val localUri: String,
    val timestamp: Long,
    val synced: Boolean = false
)