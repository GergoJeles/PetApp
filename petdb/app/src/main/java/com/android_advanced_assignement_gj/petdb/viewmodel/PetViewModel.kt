package com.android_advanced_assignement_gj.petdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android_advanced_assignement_gj.petdb.model.Pet
import com.android_advanced_assignement_gj.petdb.repository.PetRepository
import kotlinx.coroutines.launch

class PetViewModel(app: Application,
                   private val petRepository: PetRepository
):
                    AndroidViewModel(app){

                        fun addPet(pet: Pet)=
                            viewModelScope.launch {
                                petRepository.insertPet(pet)
                            }

                         fun deletePet(pet: Pet)=
                            viewModelScope.launch {
                                 petRepository.deletePet(pet)
                            }
                        fun updatePet(pet: Pet)=
                            viewModelScope.launch {
                                   petRepository.updatePet(pet)
                            }

                        fun getAllPets( )= petRepository.getAllPets()
                        fun searchPet(query: String)= petRepository.searchPet(query)
}