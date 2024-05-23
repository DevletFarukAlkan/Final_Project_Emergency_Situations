package com.devletfarukalkan.finalprojectemergencysituations.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "neighborhood",
)
data class Neighborhood(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val provinceId: Int,
    val warehouseId: Int? = null
)
