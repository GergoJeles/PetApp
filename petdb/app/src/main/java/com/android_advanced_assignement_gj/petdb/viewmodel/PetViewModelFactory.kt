package com.android_advanced_assignement_gj.petdb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android_advanced_assignement_gj.petdb.repository.PetRepository

class PetViewModelFactory(val app: Application,
                          private val petRepository: PetRepository
): ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PetViewModel(app,petRepository) as T
    }



}