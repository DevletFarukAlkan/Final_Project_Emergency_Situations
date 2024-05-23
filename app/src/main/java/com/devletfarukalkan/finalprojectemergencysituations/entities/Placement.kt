package com.devletfarukalkan.finalprojectemergencysituations.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "placement")
data class Placement (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phoneNumber: String,
    val explanation: String,
    val neighborhoodId: Int,
    val latitude: Double?,
    val longitude: Double?
)


