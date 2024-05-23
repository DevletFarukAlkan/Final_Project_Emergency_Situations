package com.devletfarukalkan.finalprojectemergencysituations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devletfarukalkan.finalprojectemergencysituations.repositories.*

class PlacementViewModelFactory(
    private val placementRepository: PlacementRepository,
    private val cityRepository: CityRepository,
    private val neighborhoodRepository: NeighborhoodRepository,
    private val provinceRepository: ProvinceRepository,
    private val warehouseRepository: WarehouseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlacementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlacementViewModel(
                placementRepository,
                cityRepository,
                neighborhoodRepository,
                provinceRepository,
                warehouseRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
