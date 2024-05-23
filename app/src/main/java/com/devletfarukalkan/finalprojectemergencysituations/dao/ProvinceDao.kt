package com.devletfarukalkan.finalprojectemergencysituations.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devletfarukalkan.finalprojectemergencysituations.entities.Province

@Dao
interface ProvinceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProvinces(provinces: List<Province>)

    @Query("SELECT * FROM province WHERE cityId = :cityId")
    suspend fun getProvincesByCity(cityId: Int): List<Province>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProvince(province: Province)

    @Query("SELECT id FROM province WHERE name = :name LIMIT 1")
    suspend fun getProvinceIdByName(name: String): Int?

    @Query("SELECT provinceId FROM neighborhood WHERE id = :id")
    suspend fun getProvinceIdByNeighborhoodId(id: Int): Int

    @Query("SELECT * FROM province WHERE id = :id")
    suspend fun getProvinceById(id: Int): Province
}
