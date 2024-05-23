package com.devletfarukalkan.finalprojectemergencysituations.repositories

import com.devletfarukalkan.finalprojectemergencysituations.dao.NeighborhoodDao
import com.devletfarukalkan.finalprojectemergencysituations.entities.Neighborhood
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NeighborhoodRepository(
    private val neighborhoodDao: NeighborhoodDao
) {

    suspend fun getNeighborhoodsByProvince(provinceId: Int): List<Neighborhood> = withContext(
        Dispatchers.IO) {
        neighborhoodDao.getNeighborhoodsByProvince(provinceId)
    }

    suspend fun updateWarehouseIdForNeighborhood(neighborhoodId: Int, warehouseId: Int) = withContext(Dispatchers.IO) {
        neighborhoodDao.updateWarehouseIdForNeighborhood(neighborhoodId, warehouseId)
    }

    suspend fun getNeighborhoodIdsByWarehouseId(warehouseId: Int): List<Int> = withContext(Dispatchers.IO) {
        neighborhoodDao.getNeighborhoodIdsByWarehouseId(warehouseId)
    }

    suspend fun getNeighborhoodById(id: Int): Neighborhood = withContext(Dispatchers.IO) {
        neighborhoodDao.getNeighborhoodById(id)
    }

    fun getWarehouseIdByNeighborhood(id: Int): Int {
        return neighborhoodDao.getWarehouseIdByNeighborhood(id)
    }
}