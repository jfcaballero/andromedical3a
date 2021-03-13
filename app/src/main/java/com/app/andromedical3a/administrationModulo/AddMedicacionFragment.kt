package com.app.andromedical3a.administrationModulo

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.app.andromedical3a.R

class AddMedicacionFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("¿Toma la medicación diariamente?")
                .setItems(arrayOf("si", "no"),
                    DialogInterface.OnClickListener { dialog, which ->
                        // The 'which' argument contains the index position
                        // of the selected item
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}