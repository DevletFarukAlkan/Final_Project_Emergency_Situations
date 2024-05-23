package com.devletfarukalkan.finalprojectemergencysituations.viewmodel

import androidx.lifecycle.*
import com.devletfarukalkan.finalprojectemergencysituations.entities.City
import com.devletfarukalkan.finalprojectemergencysituations.entities.Province
import com.devletfarukalkan.finalprojectemergencysituations.entities.Neighborhood
import com.devletfarukalkan.finalprojectemergencysituations.entities.Warehouse
import com.devletfarukalkan.finalprojectemergencysituations.entities.WarehouseDetails
import com.devletfarukalkan.finalprojectemergencysituations.repositories.CityRepository
import com.devletfarukalkan.finalprojectemergencysituations.repositories.NeighborhoodRepository
import com.devletfarukalkan.finalprojectemergencysituations.repositories.PlacementRepository
import com.devletfarukalkan.finalprojectemergencysituations.repositories.ProvinceRepository
import com.devletfarukalkan.finalprojectemergencysituations.repositories.WarehouseRepository
import kotlinx.coroutines.launch

class WarehouseViewModel(
    private val warehouseRepository: WarehouseRepository,
    private val cityRepository: CityRepository,
    private val neighborhoodRepository: NeighborhoodRepository,
    private val provinceRepository: ProvinceRepository,
    ) : ViewModel() {

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> get() = _cities

    private val _provinces = MutableLiveData<List<Province>>()
    val provinces: LiveData<List<Province>> get() = _provinces

    private val _neighborhoods = MutableLiveData<List<Neighborhood>>()
    val neighborhoods: LiveData<List<Neighborhood>> get() = _neighborhoods

    private val _warehouses = MutableLiveData<List<Warehouse>>()
    val warehouses: LiveData<List<Warehouse>> get() = _warehouses

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

    fun loadWarehouses() {
        viewModelScope.launch {
            _warehouses.value = warehouseRepository.getAllWarehouses()
        }
    }

    fun getWarehouseDetails(warehouseId: Int): LiveData<WarehouseDetails> {
        return liveData {
            val neighborhoodIds = neighborhoodRepository.getNeighborhoodIdsByWarehouseId(warehouseId)
            val provinceId = provinceRepository.getProvinceIdByNeighborhoodId(neighborhoodIds[0])
            val cityId = cityRepository.getCityIdByProvinceId(provinceId)
            emit(WarehouseDetails(cityId, provinceId, neighborhoodIds))
        }
    }

    fun addWarehouse(name: String, neighborhoodIds: IntArray) {
        viewModelScope.launch {
            val warehouse = Warehouse(name = name)
            warehouseRepository.insertWarehouse(warehouse)
            val warehouseId =warehouseRepository.getLastWarehouseId()
            for (neighborhoodId in neighborhoodIds){
                neighborhoodRepository.updateWarehouseIdForNeighborhood(neighborhoodId,warehouseId)
            }
        }
    }

    fun updateWarehouse(id: Int, name: String, neighborhoodIds: IntArray) {
        viewModelScope.launch {
            val warehouse = Warehouse(id = id, name = name)
            warehouseRepository.updateWarehouse(warehouse)
            for (neighborhoodId in neighborhoodIds){
                neighborhoodRepository.updateWarehouseIdForNeighborhood(neighborhoodId,id)
            }
        }
    }

    fun getCityById(cityId: Int): City? {
        return _cities.value?.find { it.id == cityId }
    }

    fun getProvinceById(provinceId: Int): Province? {
        return _provinces.value?.find { it.id == provinceId }
    }

    fun getNeighborhoodById(neighborhoodId: Int): Neighborhood? {
        return _neighborhoods.value?.find { it.id == neighborhoodId }
    }
}
