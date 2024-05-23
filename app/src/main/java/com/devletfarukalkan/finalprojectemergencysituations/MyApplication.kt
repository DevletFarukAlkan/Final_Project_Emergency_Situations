package com.devletfarukalkan.finalprojectemergencysituations

import android.app.Application
import androidx.room.Room
import com.devletfarukalkan.finalprojectemergencysituations.api.RetrofitClient
import com.devletfarukalkan.finalprojectemergencysituations.entities.ApiResponse
import com.devletfarukalkan.finalprojectemergencysituations.entities.City
import com.devletfarukalkan.finalprojectemergencysituations.entities.Neighborhood
import com.devletfarukalkan.finalprojectemergencysituations.entities.NeighborhoodData
import com.devletfarukalkan.finalprojectemergencysituations.entities.Province
import com.devletfarukalkan.finalprojectemergencysituations.repositories.CityRepository
import com.devletfarukalkan.finalprojectemergencysituations.repositories.NeighborhoodRepository
import com.devletfarukalkan.finalprojectemergencysituations.repositories.PlacementRepository
import com.devletfarukalkan.finalprojectemergencysituations.repositories.ProvinceRepository
import com.devletfarukalkan.finalprojectemergencysituations.repositories.WarehouseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyApplication : Application() {

    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, "app-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    val warehouseRepository: WarehouseRepository by lazy {
        WarehouseRepository(
            database.warehouseDao()
        )
    }

    val cityRepository: CityRepository by lazy {
        CityRepository(
            database.cityDao()
        )
    }

    val neighborhoodRepository : NeighborhoodRepository by lazy {
        NeighborhoodRepository(
            database.neighborhoodDao()
        )
    }

    val placementRepository : PlacementRepository by lazy {
        PlacementRepository(
            database.placementDao()
        )
    }

    val provinceRepository : ProvinceRepository by lazy {
        ProvinceRepository(
            database.provinceDao()
        )
    }

    override fun onCreate() {
        super.onCreate()
        initializeDatabase()
    }

    private fun initializeDatabase() {
        val apiService = RetrofitClient.apiService
        val call = apiService.getNeighborhoods()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val neighborhoodDataList = response.body()?.data ?: return
                    populateDatabase(neighborhoodDataList)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun populateDatabase(neighborhoodDataList: List<NeighborhoodData>) {
        CoroutineScope(Dispatchers.IO).launch {
            val citySet = mutableSetOf<String>()
            val provinceSet = mutableSetOf<Pair<String, String>>()

            for (neighborhoodData in neighborhoodDataList) {
                citySet.add(neighborhoodData.province)
                provinceSet.add(Pair(neighborhoodData.district,neighborhoodData.province))
            }

            for (cityName in citySet) {
                val city = City(name = cityName)
                val cityId = database.cityDao().getCityIdByName(cityName)
                if(cityId == null){
                    database.cityDao().insertCity(city)
                }
            }

            for ((provinceName,cityName) in provinceSet) {
                val cityId = database.cityDao().getCityIdByName(cityName)
                val province = Province(name = provinceName, cityId = cityId!!)
                val provinceId = database.provinceDao().getProvinceIdByName(provinceName)
                if(provinceId == null) {
                    database.provinceDao().insertProvince(province)
                }
            }

            for (neighborhoodData in neighborhoodDataList) {
                val provinceName = neighborhoodData.district
                val provinceId = database.provinceDao().getProvinceIdByName(provinceName)
                if (provinceId != null){
                    val neighborhoodId = database.neighborhoodDao().getNeighborhoodIdByName(neighborhoodData.name)
                    if (neighborhoodId == null){
                        val neighborhood = Neighborhood(
                            name = neighborhoodData.name,
                            provinceId = provinceId
                        )
                        database.neighborhoodDao().insertNeighborhood(neighborhood)
                    }
                }
            }
        }
    }

}
