package com.devletfarukalkan.finalprojectemergencysituations.repositories

import com.devletfarukalkan.finalprojectemergencysituations.dao.ProvinceDao
import com.devletfarukalkan.finalprojectemergencysituations.entities.Province
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProvinceRepository(
    private val provinceDao: ProvinceDao
) {
    suspend fun getProvincesByCity(cityId: Int): List<Province> = withContext(Dispatchers.IO) {
        provinceDao.getProvincesByCity(cityId)
    }

    suspend fun getProvinceIdByNeighborhoodId(neighborhoodId: Int): Int = withContext(Dispatchers.IO) {
        provinceDao.getProvinceIdByNeighborhoodId(neighborhoodId)
    }

    suspend fun getProvinceById(id: Int): Province = withContext(Dispatchers.IO) {
        provinceDao.getProvinceById(id)
    }
}