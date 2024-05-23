package com.devletfarukalkan.finalprojectemergencysituations.entities

data class WarehouseDetails(
    val cityId: Int,
    val provinceId: Int,
    val neighborhoodIds: List<Int>
)