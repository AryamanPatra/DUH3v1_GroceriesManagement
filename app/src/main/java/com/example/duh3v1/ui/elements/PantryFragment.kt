package com.example.duh3v1.ui.elements

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duh3v1.ui.utils.PantryAdapter
import com.example.duh3v1.R
import com.example.duh3v1.data.models.Item
import com.example.duh3v1.data.models.MetricUnit
import com.example.duh3v1.ui.states.ItemViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.runBlocking
import java.lang.Float

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PantryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PantryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var pantryAdapter: PantryAdapter
    private lateinit var displayItemsRvPantry: RecyclerView
    private lateinit var addItemFab: FloatingActionButton
    private lateinit var dataSet: MutableList<Item>
    private lateinit var itemViewModel: ItemViewModel

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
        val view = inflater.inflate(R.layout.fragment_pantry, container, false)
        displayItemsRvPantry = view.findViewById(R.id.displayItemsRvPantry)
        addItemFab = view.findViewById(R.id.addItemFabPantry)
        //        Initializing "Item" viewModel for pantry fragment
        itemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Test dataset
        dataSet = arrayOf(
            Item(0,"Apple",1f,0f,0f,MetricUnit.KG,"Fruit",null),
            Item(0,"Garlic",400f,0f,0f,MetricUnit.G,"Veggie",null),

        ).toMutableList()

        pantryAdapter = PantryAdapter(requireActivity(),dataSet) {
            // Edit Item Bottom Sheet Fragment loads
            val editItemBSFragment = EditItemBSFragment(it)
            editItemBSFragment.show(parentFragmentManager,editItemBSFragment.tag)
        }
        val gridLayoutManager = GridLayoutManager(requireActivity(),2,
            GridLayoutManager.VERTICAL,false)
        displayItemsRvPantry.layoutManager = gridLayoutManager
        displayItemsRvPantry.adapter = pantryAdapter
//        reloadDataset()
        itemViewModel.getAllItems().observe(viewLifecycleOwner, Observer {items ->
            pantryAdapter.setData(items)
        })


        // to add item
        addItemFab.setOnClickListener{
            addItemViaDialogBox()
        }

        //to edit / delete item
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PantryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PantryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    // Utils
    private fun addItemViaDialogBox(){
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.add_item_pantry_dbox)
        val itemUnitActv = dialog.findViewById<AutoCompleteTextView>(R.id.itemUnitActvAipdb)
        val addBtn = dialog.findViewById<Button>(R.id.addItemBtnAipdb)
        val cancelBtn = dialog.findViewById<Button>(R.id.cancelItemBtnAipdb)

        //For adding metric units to drop menu
        val metricUnitArr = MetricUnit.entries.toTypedArray()
        val metricUnitAdapter = ArrayAdapter(requireContext(),R.layout.item_unit_dropdown_item,metricUnitArr)
        itemUnitActv.setAdapter(metricUnitAdapter)

        val itemName = dialog.findViewById<TextInputEditText>(R.id.itemNameTietAipdb)
        val itemQuantity = dialog.findViewById<TextInputEditText>(R.id.itemQuantityTietAipdb)
        val itemCategory = dialog.findViewById<TextInputEditText>(R.id.itemCategoryTietAipdb)
        addBtn.setOnClickListener {
            if (itemName.text.toString().isNotBlank() && itemQuantity.text.toString().isNotBlank() && itemCategory.text.toString().isNotBlank()){
                var selectedUnit: MetricUnit = MetricUnit.PC
                for (i in metricUnitArr){
                    if (i.toString()==itemUnitActv.text.toString())
                        selectedUnit = i
                }
                val newItem = Item(
                    0,
                    itemName.text.toString(),
                    Float.parseFloat(itemQuantity.text.toString()),
                    0f,
                    0f,
                    selectedUnit,
                    itemCategory.text.toString(),
                    null
                )
                dataSet.add(newItem)
                displayItemsRvPantry.adapter?.notifyItemInserted(dataSet.size-1)

                runBlocking {
                    val id = itemViewModel.upsert(newItem)
                    Log.d("AutoGeneration check-in",id.toString())
                    Log.d("sizeofEntity", itemViewModel.getAllItems().value?.size.toString())
                }
                dialog.dismiss()
            }
            else{
                Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
            }
        }
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}