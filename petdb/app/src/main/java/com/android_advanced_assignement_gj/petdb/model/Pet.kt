package com.android_advanced_assignement_gj.petdb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "pets")
@Parcelize
data class Pet(
    @PrimaryKey(autoGenerate = true) val id: Int,

    // PET
    @ColumnInfo(name = "petName") val petName: String,
    @ColumnInfo(name = "petBloodType") val petBloodType: BloodType?, // Make it nullable

    // Owner
    @ColumnInfo(name = "petOwnerName") val petOwnerName: String,
    @ColumnInfo(name = "petOwnerAddress") val petOwnerAddress: String
) : Parcelable
