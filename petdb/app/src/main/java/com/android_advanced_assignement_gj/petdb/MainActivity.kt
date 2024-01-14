package com.android_advanced_assignement_gj.petdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.android_advanced_assignement_gj.petdb.database.PetDatabase
import com.android_advanced_assignement_gj.petdb.databinding.ActivityMainBinding
import com.android_advanced_assignement_gj.petdb.repository.PetRepository
import com.android_advanced_assignement_gj.petdb.viewmodel.PetViewModel
import com.android_advanced_assignement_gj.petdb.viewmodel.PetViewModelFactory


class MainActivity : AppCompatActivity() {


    lateinit var binding:ActivityMainBinding

    lateinit var petViewModel : PetViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val petRepository = PetRepository(PetDatabase(this))

    val viewModelProviderFactory = PetViewModelFactory(application, petRepository)

        petViewModel = ViewModelProvider(this, viewModelProviderFactory).get(PetViewModel::class.java)

    }
}