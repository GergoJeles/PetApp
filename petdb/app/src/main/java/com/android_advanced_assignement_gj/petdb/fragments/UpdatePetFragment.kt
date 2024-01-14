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
import androidx.navigation.fragment.navArgs
import com.android_advanced_assignement_gj.petdb.MainActivity
import com.android_advanced_assignement_gj.petdb.R
import com.android_advanced_assignement_gj.petdb.databinding.FragmentUpdatePetBinding
import com.android_advanced_assignement_gj.petdb.dialogs.DeleteConfirmationDialog
import com.android_advanced_assignement_gj.petdb.model.*
import com.android_advanced_assignement_gj.petdb.viewmodel.PetViewModel
import com.android_advanced_assignement_gj.petdb.model.BloodType




class UpdatePetFragment : Fragment(R.layout.fragment_update_pet) {

    private var _binding: FragmentUpdatePetBinding? = null
    private val binding get() = _binding!!
    private lateinit var petViewModel: PetViewModel
    private val args: UpdatePetFragmentArgs by navArgs()
    private lateinit var currentPet: Pet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdatePetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        petViewModel = (activity as MainActivity).petViewModel
        currentPet = args.pet!!

        binding.btnGoBackWithoutEditing.setOnClickListener {
            // Use findNavController to navigate back to the home fragment
            it.findNavController().navigate(R.id.action_updatePetFragment_to_homeFragment)
        }

        //pet

        binding.etPetNameUpdate.setText(currentPet.petName)
        val bloodTypeButtonId = when (currentPet.petBloodType) {
            BloodType.A -> R.id.rbBloodTypeAUpdate
            BloodType.B -> R.id.rbBloodTypeBUpdate
            BloodType.AB -> R.id.rbBloodTypeABUpdate
            else -> -1
        }
        if (bloodTypeButtonId != -1) {
            binding.rgBloodTypeUpdate.check(bloodTypeButtonId)
        }

        //owner

        binding.etPetOwnerNameUpdate.setText(currentPet.petOwnerName)
        binding.etPetOwnerAddressUpdate.setText(currentPet.petOwnerAddress)

        binding.fabDone.setOnClickListener {

            val petName = binding.etPetNameUpdate.text.toString().trim()
            val petBloodType = when (binding.rgBloodTypeUpdate.checkedRadioButtonId) {
                R.id.rbBloodTypeAUpdate -> BloodType.A
                R.id.rbBloodTypeBUpdate -> BloodType.B
                R.id.rbBloodTypeABUpdate -> BloodType.AB
                else -> null
            }
            val petOwnerName = binding.etPetOwnerNameUpdate.text.toString().trim()
            val petOwnerAddress = binding.etPetOwnerAddressUpdate.text.toString().trim()

            if (petName.isNotEmpty()) {
                val pet = Pet(currentPet.id, petName, petBloodType, petOwnerName, petOwnerAddress)
                petViewModel.updatePet(pet)
                view.findNavController().navigate(R.id.action_updatePetFragment_to_homeFragment)
            } else {
                Toast.makeText(
                    context,
                    "Please fill in all the fields",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showDeleteConfirmationDialog() {
        val deleteConfirmationDialog = DeleteConfirmationDialog {
            // This block will be executed when the user confirms deletion
            petViewModel.deletePet(currentPet)
            view?.findNavController()?.navigate(R.id.action_updatePetFragment_to_homeFragment)
        }
        deleteConfirmationDialog.show(childFragmentManager, "deleteConfirmation")
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_pet_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                showDeleteConfirmationDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
