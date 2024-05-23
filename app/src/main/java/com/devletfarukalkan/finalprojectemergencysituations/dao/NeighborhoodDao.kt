package com.devletfarukalkan.finalprojectemergencysituations.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devletfarukalkan.finalprojectemergencysituations.entities.Neighborhood

@Dao
interface NeighborhoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNeighborhood(neighborhood: Neighborhood)

    @Query("SELECT * FROM neighborhood WHERE provinceId = :provinceId")
    suspend fun getNeighborhoodsByProvince(provinceId: Int): List<Neighborhood>

    @Query("UPDATE neighborhood SET warehouseId = :warehouseId WHERE id = :neighborhoodId")
    suspend fun updateWarehouseIdForNeighborhood(neighborhoodId: Int, warehouseId: Int)

    @Query("SELECT id FROM neighborhood WHERE name = :name LIMIT 1")
    suspend fun getNeighborhoodIdByName(name: String): Int?

    @Query("SELECT id FROM neighborhood WHERE warehouseId = :warehouseId")
    suspend fun getNeighborhoodIdsByWarehouseId(warehouseId: Int): List<Int>

    @Query("SELECT * FROM neighborhood WHERE id = :id")
    suspend fun getNeighborhoodById(id: Int): Neighborhood

    @Query("SELECT warehouseId FROM neighborhood WHERE id = :id")
    fun getWarehouseIdByNeighborhood(id: Int): Int
}
