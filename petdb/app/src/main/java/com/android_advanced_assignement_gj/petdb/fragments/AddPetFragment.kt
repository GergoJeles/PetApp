package com.android_advanced_assignement_gj.petdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android_advanced_assignement_gj.petdb.MainActivity
import com.android_advanced_assignement_gj.petdb.R
import com.android_advanced_assignement_gj.petdb.adapter.PetAdapter
import com.android_advanced_assignement_gj.petdb.databinding.FragmentNewPetBinding
import com.android_advanced_assignement_gj.petdb.model.Pet
import com.android_advanced_assignement_gj.petdb.viewmodel.PetViewModel
import com.android_advanced_assignement_gj.petdb.model.BloodType




class AddPetFragment : Fragment(R.layout.fragment_new_pet) {

    private var _binding : FragmentNewPetBinding? = null
    private val binding get()= _binding!!
    private lateinit var  petsViewModel: PetViewModel
    private lateinit var petAdapter: PetAdapter
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewPetBinding.inflate(inflater, container, false)
        return binding.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        petsViewModel = (activity as MainActivity).petViewModel
        mView = view

        binding.btnGoBackWithoutEditing.setOnClickListener {
            it.findNavController().navigate(R.id.action_newPetFragment_to_homeFragment)
        }
    }
    private fun savepet(view: View) {
        val petName = binding.etPetName.text.toString().trim()
        val petOwnerName = binding.etPetOwnerName.text.toString().trim()
        val petOwnerAddress = binding.etPetOwnerAddress.text.toString().trim()
        val petBloodType = when (binding.rgBloodType.checkedRadioButtonId) {
            R.id.rbBloodTypeA -> BloodType.A
            R.id.rbBloodTypeB -> BloodType.B
            R.id.rbBloodTypeAB -> BloodType.AB
            else -> null
        }

        if (petName.isNotEmpty() && petBloodType != null) {
            val pet = Pet(0, petName, petBloodType, petOwnerName, petOwnerAddress)

            petsViewModel.addPet(pet)

            Toast.makeText(mView.context,
                "Pet's data saved successfully",
                Toast.LENGTH_LONG).show()

            view.findNavController().navigate(R.id.action_newPetFragment_to_homeFragment)
        } else {
            Toast.makeText(mView.context,
                "Please enter all the data about the pet",
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.new_pet_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save ->{
                savepet(mView)
            }
        }

        return super.onOptionsItemSelected(item)
    }
    }