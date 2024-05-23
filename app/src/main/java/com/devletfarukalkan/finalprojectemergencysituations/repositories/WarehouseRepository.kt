package com.devletfarukalkan.finalprojectemergencysituations.repositories

import com.devletfarukalkan.finalprojectemergencysituations.dao.WarehouseDao
import com.devletfarukalkan.finalprojectemergencysituations.entities.Warehouse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WarehouseRepository(
    private val warehouseDao: WarehouseDao
) {
    suspend fun insertWarehouse(warehouse: Warehouse): Long = withContext(Dispatchers.IO) {
        warehouseDao.insertWarehouse(warehouse)
    }

    suspend fun updateWarehouse(warehouse: Warehouse) = withContext(Dispatchers.IO) {
        warehouseDao.updateWarehouse(warehouse)
    }

    suspend fun getAllWarehouses(): List<Warehouse> = withContext(Dispatchers.IO) {
        warehouseDao.getAllWarehouses()
    }

    suspend fun getLastWarehouseId(): Int = withContext(Dispatchers.IO) {
        warehouseDao.getLastWarehouseId()
    }

    fun getWarehouseNameById(id: Int): String {
        return warehouseDao.getWarehouseNameById(id)
    }
}
