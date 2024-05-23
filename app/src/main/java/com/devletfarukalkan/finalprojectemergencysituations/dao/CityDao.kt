package com.devletfarukalkan.finalprojectemergencysituations.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devletfarukalkan.finalprojectemergencysituations.entities.City

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<City>)

    @Query("SELECT * FROM city")
    suspend fun getAllCities(): List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: City)

    @Query("SELECT id FROM City WHERE name = :name LIMIT 1")
    suspend fun getCityIdByName(name: String): Int?

    @Query("SELECT cityId FROM province WHERE id = :id")
    suspend fun getCityIdByProvinceId(id: Int): Int

    @Query("SELECT * FROM city WHERE id = :id")
    suspend fun getCityById(id: Int): City
}
