package com.devletfarukalkan.finalprojectemergencysituations.repositories

import com.devletfarukalkan.finalprojectemergencysituations.dao.CityDao
import com.devletfarukalkan.finalprojectemergencysituations.entities.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityRepository(
    private val cityDao: CityDao
) {

    suspend fun getAllCities(): List<City> = withContext(Dispatchers.IO) {
        cityDao.getAllCities()
    }

    suspend fun getCityIdByProvinceId(provinceId: Int): Int = withContext(Dispatchers.IO) {
        cityDao.getCityIdByProvinceId(provinceId)
    }

    suspend fun getCityById(id: Int): City = withContext(Dispatchers.IO) {
        cityDao.getCityById(id)
    }
}