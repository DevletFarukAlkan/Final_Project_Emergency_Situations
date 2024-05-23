package com.devletfarukalkan.finalprojectemergencysituations.entities

data class NeighborhoodData(
    val provinceId: Int,
    val districtId: Int,
    val id: Int,
    val province: String,
    val district: String,
    val name: String,
    val population: Int
)