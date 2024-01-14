package com.android_advanced_assignement_gj.petdb.repository

import com.android_advanced_assignement_gj.petdb.database.PetDatabase
import com.android_advanced_assignement_gj.petdb.model.Pet

class PetRepository(private val db: PetDatabase) {

    suspend fun insertPet(pet: Pet)= db.getPetDao().insertPet(pet)
    suspend fun deletePet(pet: Pet)= db.getPetDao().deletePet(pet)
    suspend fun updatePet(pet: Pet)= db.getPetDao().updatePet(pet)

    fun getAllPets()= db.getPetDao().getAllPets()
    fun searchPet(query: String?)= db.getPetDao().searchPet(query)
}