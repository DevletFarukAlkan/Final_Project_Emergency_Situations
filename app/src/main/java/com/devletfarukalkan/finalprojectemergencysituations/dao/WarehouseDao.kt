package com.devletfarukalkan.finalprojectemergencysituations.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.devletfarukalkan.finalprojectemergencysituations.entities.Warehouse

@Dao
interface WarehouseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWarehouse(warehouse: Warehouse): Long

    @Update
    suspend fun updateWarehouse(warehouse: Warehouse)

    @Transaction
    @Query("SELECT * FROM warehouse")
    suspend fun getAllWarehouses(): List<Warehouse>

    @Query("SELECT Id FROM warehouse ORDER BY Id")
    suspend fun getLastWarehouseId(): Int

    @Query("SELECT Name FROM warehouse WHERE id = :id")
    fun getWarehouseNameById(id: Int): String
}
