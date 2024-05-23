package com.devletfarukalkan.finalprojectemergencysituations.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devletfarukalkan.finalprojectemergencysituations.entities.Placement

@Dao
interface PlacementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlacement(placement: Placement): Long

    @Query("SELECT * FROM placement WHERE id = :id")
    suspend fun getPlacementById(id: Int): Placement

    @Query("SELECT * FROM placement")
    suspend fun getAllPlacements(): List<Placement>
}