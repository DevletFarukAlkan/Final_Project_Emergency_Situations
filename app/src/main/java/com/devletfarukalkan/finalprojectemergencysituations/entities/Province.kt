package com.devletfarukalkan.finalprojectemergencysituations.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "province")
data class Province(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val cityId: Int
)