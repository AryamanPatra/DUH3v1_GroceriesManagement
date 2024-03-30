package com.example.duh3v1.ui.elements

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.duh3v1.R
import com.example.duh3v1.data.models.Item
import com.example.duh3v1.data.models.MetricUnit
import com.example.duh3v1.ui.states.ItemViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.runBlocking

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditItemBSFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditItemBSFragment(
    val selectedItem: Item
) : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_item_b_s, container, false)
        val itemName = view.findViewById<TextInputEditText>(R.id.itemNameTietEibs)
        val itemQuantity = view.findViewById<TextInputEditText>(R.id.itemQuantityTietEibs)
        val itemCategory = view.findViewById<TextInputEditText>(R.id.itemCategoryTietEibs)
        val itemUnitActv = view.findViewById<AutoCompleteTextView>(R.id.itemUnitActvEibs)
        val editableInstance = Editable.Factory.getInstance()
        itemName.text = editableInstance.newEditable(selectedItem.name)
        itemQuantity.text = editableInstance.newEditable((selectedItem.qLeft+selectedItem.qReserved+selectedItem.qUsed).toString())
        itemCategory.text = editableInstance.newEditable(selectedItem.category)
        itemUnitActv.text = editableInstance.newEditable(selectedItem.unit.toString())

        //For adding metric units to drop menu
        val metricUnitArr = MetricUnit.entries.toTypedArray()
        val metricUnitAdapter = ArrayAdapter(requireContext(),R.layout.item_unit_dropdown_item,metricUnitArr)
        itemUnitActv.setAdapter(metricUnitAdapter)

        //Action buttons
        val deleteBtn = view.findViewById<Button>(R.id.deleteBtnEibs)
        val saveChangesBtn = view.findViewById<Button>(R.id.saveChangesBtnEibs)

        val itemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        deleteBtn.setOnClickListener{deleteItem(itemViewModel)}
        saveChangesBtn.setOnClickListener {saveChangesItem(itemViewModel)}

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditItemBSFragment.
         */

    }

    private fun deleteItem(itemViewModel: ItemViewModel){
        val alertDialogBuilder =  AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setMessage("Delete item "+selectedItem.name+"?")
        alertDialogBuilder.setTitle("Delete")
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton("Confirm"){ dialog,which ->
            runBlocking {
                itemViewModel.delete(selectedItem)
                Log.d("Deletion",selectedItem.name+" deleted")
                dialog.dismiss()
            }
//            requireParentFragment().parentFragmentManager.beginTransaction().remove(this).commit()
        }
        alertDialogBuilder.setNegativeButton("Cancel"){ dialog,which ->
            dialog.dismiss()
        }
        alertDialogBuilder.create().show()
    }
    private fun saveChangesItem(itemViewModel: ItemViewModel){
        val alertDialogBuilder =  AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setMessage("Update item "+selectedItem.name+"?")
        alertDialogBuilder.setTitle("Save Changes")
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton("Confirm"){ dialog,which ->
            runBlocking {
                itemViewModel.upsert(selectedItem)
                Log.d("Update",selectedItem.name+" update")
                dialog.dismiss()
            }
            requireParentFragment().parentFragmentManager.beginTransaction().remove(this).commit()
        }
        alertDialogBuilder.setNegativeButton("Cancel"){ dialog,which ->
            dialog.dismiss()
        }
        alertDialogBuilder.create().show()
    }
}