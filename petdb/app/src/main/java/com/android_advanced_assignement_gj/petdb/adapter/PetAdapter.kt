package com.android_advanced_assignement_gj.petdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android_advanced_assignement_gj.petdb.fragments.*
import com.android_advanced_assignement_gj.petdb.databinding.PetLayoutBinding
import com.android_advanced_assignement_gj.petdb.model.Pet


class PetAdapter : RecyclerView.Adapter <PetAdapter.PetViewHolder>(){

    class PetViewHolder(val itemBinding: PetLayoutBinding):
            RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Pet>(){
        override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean {
            return oldItem.id == newItem.id && oldItem.petName == newItem.petName &&
                    oldItem.petOwnerName == newItem.petOwnerName
        }

        override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        return PetViewHolder(PetLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val currentPet = differ.currentList[position]
        holder.itemBinding.tvPetName.text = currentPet.petName
        holder.itemBinding.tvPetBloodType.text = currentPet.petBloodType?.type ?: "Unknown"
        holder.itemBinding.tvPetOwnerName.text = currentPet.petOwnerName
        holder.itemBinding.tvPetOwnerAddress.text = currentPet.petOwnerAddress


        holder.itemBinding.ibNumber.text = (position + 1).toString()

        holder.itemView.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdatePetFragment(currentPet)
            it.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}