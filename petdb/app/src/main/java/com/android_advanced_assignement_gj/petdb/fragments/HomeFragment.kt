package com.android_advanced_assignement_gj.petdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android_advanced_assignement_gj.petdb.MainActivity
import com.android_advanced_assignement_gj.petdb.R
import com.android_advanced_assignement_gj.petdb.adapter.PetAdapter
import com.android_advanced_assignement_gj.petdb.databinding.FragmentHomeBinding
import com.android_advanced_assignement_gj.petdb.dialogs.DeleteConfirmationDialog
import com.android_advanced_assignement_gj.petdb.model.Pet
import com.android_advanced_assignement_gj.petdb.viewmodel.PetViewModel


class HomeFragment : Fragment(
    R.layout.fragment_home
), SearchView.OnQueryTextListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var petsViewModel: PetViewModel
    private lateinit var petAdapter: PetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        petsViewModel = (activity as MainActivity).petViewModel

        setUpRecyclerView()
        binding.fabAddPet.setOnClickListener { it.findNavController().navigate(R.id.action_homeFragment_to_newPetFragment) }
    }

    private fun showDeleteConfirmationDialog(petToDelete: Pet) {
        val deleteConfirmationDialog = DeleteConfirmationDialog {
            // This block will be executed when the user confirms deletion
            petsViewModel.deletePet(petToDelete)
        }
        deleteConfirmationDialog.show(childFragmentManager, "deleteConfirmation")
    }

    private fun setUpRecyclerView() {
        petAdapter = PetAdapter()
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                1, StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = petAdapter
        }

        // Create an ItemTouchHelper for swipe actions
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, // dragDirs
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // swipeDirs (both left and right)
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Swipe to delete action
                val petToDelete = petAdapter.differ.currentList[viewHolder.adapterPosition]
                showDeleteConfirmationDialog(petToDelete)
            }
        }


        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        activity?.let {
            petsViewModel.getAllPets().observe(viewLifecycleOwner, { pet ->
                petAdapter.differ.submitList(pet)
                updateUI(pet)
            })
        }
    }

    private fun updateUI(pet: List<Pet>?) {
        if (pet != null) {
            if (pet.isNotEmpty()) {
                binding.cardView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            } else {
                binding.cardView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)
        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled = false
        mMenuSearch.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //  searchPet(query)
        return false
    }

    private fun searchPet(query: String?) {
        val searchQuery = "%$query"
        petsViewModel.searchPet(searchQuery)
            .observe(this, { list -> petAdapter.differ.submitList(list) })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchPet(newText)
        }
        return true
    }
}
