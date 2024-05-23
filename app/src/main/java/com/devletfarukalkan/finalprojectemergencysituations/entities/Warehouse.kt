package com.devletfarukalkan.finalprojectemergencysituations.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "warehouse")
data class Warehouse(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
