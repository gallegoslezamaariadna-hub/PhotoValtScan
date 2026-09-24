package com.photovaltscan.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.photovaltscan.app.data.local.entity.MediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(media: MediaEntity)

    @Query("SELECT * FROM media_files WHERE projectId = :projectId AND sectionId = :sectionId")
    fun getMediaForSection(projectId: String, sectionId: String): Flow<List<MediaEntity>>

    @Query("DELETE FROM media_files WHERE id = :mediaId")
    suspend fun deleteMedia(mediaId: String)
}