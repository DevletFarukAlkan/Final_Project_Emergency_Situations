package com.devletfarukalkan.finalprojectemergencysituations.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.devletfarukalkan.finalprojectemergencysituations.entities.City
import com.devletfarukalkan.finalprojectemergencysituations.entities.Neighborhood
import com.devletfarukalkan.finalprojectemergencysituations.entities.Placement
import com.devletfarukalkan.finalprojectemergencysituations.entities.Province
import com.devletfarukalkan.finalprojectemergencysituations.repositories.*
import kotlinx.coroutines.launch

class PlacementViewModel(
    private val placementRepository: PlacementRepository,
    private val cityRepository: CityRepository,
    private val neighborhoodRepository: NeighborhoodRepository,
    private val provinceRepository: ProvinceRepository,
    private val warehouseRepository: WarehouseRepository
) : ViewModel() {

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> get() = _cities

    private val _provinces = MutableLiveData<List<Province>>()
    val provinces: LiveData<List<Province>> get() = _provinces

    private val _neighborhoods = MutableLiveData<List<Neighborhood>>()
    val neighborhoods: LiveData<List<Neighborhood>> get() = _neighborhoods

    private val _placements = MutableLiveData<List<Placement>>()
    val placements: LiveData<List<Placement>> get() = _placements

    fun loadPlacements() {
        viewModelScope.launch {
            _placements.value = placementRepository.getAllPlacements()
        }
    }

    fun loadCities() {
        viewModelScope.launch {
            _cities.value = cityRepository.getAllCities()
        }
    }

    fun loadProvincesByCity(cityId: Int) {
        viewModelScope.launch {
            _provinces.value = provinceRepository.getProvincesByCity(cityId)
        }
    }

    fun loadNeighborhoodsByProvince(provinceId: Int) {
        viewModelScope.launch {
            _neighborhoods.value = neighborhoodRepository.getNeighborhoodsByProvince(provinceId)
        }
    }

    fun getPlacementById(placementId: Int): LiveData<Placement> {
        return liveData {
            val placement = placementRepository.getPlacementById(placementId)
            emit(placement)
        }
    }

    fun getNeighborhoodById(neighborhoodId: Int): LiveData<Neighborhood> {
        return liveData {
            val neighborhood = neighborhoodRepository.getNeighborhoodById(neighborhoodId)
            emit(neighborhood)
        }
    }

    fun getProvinceById(provinceId: Int): LiveData<Province> {
        return liveData {
            val province = provinceRepository.getProvinceById(provinceId)
            emit(province)
        }
    }

    fun getCityById(cityId: Int): LiveData<City> {
        return liveData {
            val city = cityRepository.getCityById(cityId)
            emit(city)
        }
    }

    fun getWarehouseNameByNeighborhoodId(neighborhoodId: Int): String {
        val warehouseId = neighborhoodRepository.getWarehouseIdByNeighborhood(neighborhoodId)
        return warehouseRepository.getWarehouseNameById(warehouseId)
    }

    fun addPlacement(name: String, phoneNumber: String, explanation: String, neighborhoodId: Int, latitude: Double?, longitude: Double?){
    viewModelScope.launch {
        val placement = Placement(name = name, phoneNumber = phoneNumber, explanation = explanation, neighborhoodId = neighborhoodId, latitude = latitude, longitude = longitude)
        placementRepository.insertPlacement(placement)
        }
    }
}
