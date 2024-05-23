package com.devletfarukalkan.finalprojectemergencysituations

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devletfarukalkan.finalprojectemergencysituations.dao.*
import com.devletfarukalkan.finalprojectemergencysituations.entities.*

@Database(
    entities = [
        City::class,
        Province::class,
        Neighborhood::class,
        Warehouse::class,
        Placement::class
    ],
    version = 8,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun provinceDao(): ProvinceDao
    abstract fun neighborhoodDao(): NeighborhoodDao
    abstract fun warehouseDao(): WarehouseDao
    abstract fun placementDao(): PlacementDao
}
