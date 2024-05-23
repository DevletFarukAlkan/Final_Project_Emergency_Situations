package com.devletfarukalkan.finalprojectemergencysituations.entities

data class ApiResponse(
    val status: String,
    val data: List<NeighborhoodData>
)