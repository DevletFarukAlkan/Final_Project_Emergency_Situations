package com.devletfarukalkan.finalprojectemergencysituations.repositories

import com.devletfarukalkan.finalprojectemergencysituations.dao.PlacementDao
import com.devletfarukalkan.finalprojectemergencysituations.entities.Placement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacementRepository(
    private val placementDao: PlacementDao
) {

    suspend fun insertPlacement(placement: Placement): Long = withContext(Dispatchers.IO) {
        placementDao.insertPlacement(placement)
    }

    suspend fun getPlacementById(id: Int): Placement = withContext(Dispatchers.IO) {
        placementDao.getPlacementById(id)
    }

    suspend fun getAllPlacements(): List<Placement> = withContext(Dispatchers.IO) {
        placementDao.getAllPlacements()
    }

}