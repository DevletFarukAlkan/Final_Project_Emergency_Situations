package com.devletfarukalkan.finalprojectemergencysituations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devletfarukalkan.finalprojectemergencysituations.repositories.CityRepository
import com.devletfarukalkan.finalprojectemergencysituations.repositories.NeighborhoodRepository
import com.devletfarukalkan.finalprojectemergencysituations.repositories.PlacementRepository
import com.devletfarukalkan.finalprojectemergencysituations.repositories.ProvinceRepository
import com.devletfarukalkan.finalprojectemergencysituations.repositories.WarehouseRepository

class WarehouseViewModelFactory(
    private val warehouseRepository: WarehouseRepository,
    private val cityRepository: CityRepository,
    private val neighborhoodRepository: NeighborhoodRepository,
    private val provinceRepository: ProvinceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WarehouseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WarehouseViewModel(warehouseRepository,cityRepository,neighborhoodRepository,provinceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
