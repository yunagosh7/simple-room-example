package com.example.roomexample3.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.roomexample3.R
import com.example.roomexample3.database.AppDatabase
import com.example.roomexample3.database.Product
import com.example.roomexample3.databinding.ActivityMainBinding
import com.example.roomexample3.model.ProductCategories
import com.example.roomexample3.toast
import com.example.roomexample3.viewmodel.ProductViewModelFactory
import com.example.roomexample3.viewmodel.ProductsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase
    private lateinit var viewModel: ProductsViewModel
    lateinit var product: Product

    private lateinit var categorySelected: ProductCategories
    private lateinit var spinnerAdapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        val viewModelFactory = ProductViewModelFactory(database.productDao())
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductsViewModel::class.java)

        initUI()
        initListeners()

    }

    private fun initUI() {
        val spinnerOptions: Array<String> = resources.getStringArray(R.array.spinner_options)
        spinnerAdapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerOptions
        )
        binding.spinnerCategories.adapter = spinnerAdapter

    }

    private fun initListeners() {
        binding.btnAddUser.setOnClickListener {
            addProduct()
        }

        binding.btnNavigate.setOnClickListener {
            navigateToTasksActivity()
        }

        binding.spinnerCategories.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                categorySelected = when(selectedItem) {
                    "Tecnología" -> ProductCategories.TECNOLOGIA
                    "Electrodomesticos" -> ProductCategories.ELECTRODOMESTICOS
                    "Deportes" -> ProductCategories.DEPORTES
                    "Vestimenta" -> ProductCategories.VESTIMENTA
                    else -> ProductCategories.OTROS // Valor por defecto si no coincide con ninguna opción
                }
            }


            override fun onNothingSelected(p0: AdapterView<*>?) {
                categorySelected = ProductCategories.OTROS
            }
        }
    }


    private fun addProduct() {
        CoroutineScope(Dispatchers.IO).launch {
            if(isEntryValid()) {
                viewModel.addNewItem(
                    binding.etName.text.toString(),
                    binding.etPrice.text.toString(),
                    categorySelected
                )
                runOnUiThread {
                    toast("Anduvo")
                    resetFields()
                }

            } else {
                toast("Porfavor ingrese valores en los campos")
            }
        }

    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.etName.text.toString(),
            binding.etPrice.text.toString()
        )
    }

    private fun resetFields() {
        binding.etName.text.clear()
        binding.etPrice.text.clear()
        binding.spinnerCategories.setSelection(0)
        categorySelected = ProductCategories.OTROS
    }

    private fun navigateToTasksActivity() {
        val intent = Intent(this, ProductListActivity::class.java)
        startActivity(intent)
    }



}