package com.photovaltscan.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.photovaltscan.app.data.local.dao.ProjectDao
import com.photovaltscan.app.data.local.entity.ProjectEntity
import com.photovaltscan.app.data.local.entity.VisitEntity

import com.photovaltscan.app.data.local.dao.MediaDao
import com.photovaltscan.app.data.local.entity.MediaEntity

@Database(
    entities = [ProjectEntity::class, VisitEntity::class, MediaEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val projectDao: ProjectDao
    abstract val mediaDao: MediaDao
}
