package com.photovaltscan.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.photovaltscan.app.data.local.dao.ProjectDao
import com.photovaltscan.app.data.local.entity.ProjectEntity
import com.photovaltscan.app.data.local.entity.VisitEntity

@Database(
    entities = [ProjectEntity::class, VisitEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val projectDao: ProjectDao
}
