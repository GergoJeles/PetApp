package com.android_advanced_assignement_gj.petdb.database

import androidx.room.TypeConverter
import com.android_advanced_assignement_gj.petdb.model.BloodType

class Converters {
    @TypeConverter
    fun fromBloodType(bloodType: BloodType): String {
        return bloodType.type
    }

    @TypeConverter
    fun toBloodType(bloodType: String): BloodType {
        return BloodType.values().find { it.type == bloodType }
            ?: throw IllegalArgumentException("Unknown Blood Type")
    }
}