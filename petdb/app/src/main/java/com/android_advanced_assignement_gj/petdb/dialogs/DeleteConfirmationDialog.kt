package com.android_advanced_assignement_gj.petdb.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DeleteConfirmationDialog(private val onDeleteConfirmed: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Delete Pet")
            .setMessage("Are you sure you want to delete this Pet?")
            .setPositiveButton("Delete") { _, _ ->
                onDeleteConfirmed()
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
