package com.example.roomexample3.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomexample3.R
import com.example.roomexample3.adapters.ProductsAdapter
import com.example.roomexample3.database.AppDatabase
import com.example.roomexample3.database.Product
import com.example.roomexample3.databinding.ActivityTaskListBinding
import com.example.roomexample3.model.ProductCategories
import com.example.roomexample3.toast
import com.example.roomexample3.viewmodel.ProductListViewModel
import com.example.roomexample3.viewmodel.ProductListViewModelFactory


class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskListBinding
    private lateinit var database: AppDatabase
    private lateinit var viewModel: ProductListViewModel

    private lateinit var adapter: ProductsAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var categorySelected: ProductCategories


    // Componentes del Dialog
    private lateinit var etProductName: EditText
    private lateinit var etProductPrice: EditText
    private lateinit var spinner: Spinner
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        val viewModelFactory = ProductListViewModelFactory(database.productDao())
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductListViewModel::class.java)

        initObservers()

    }

    private fun initObservers() {
        viewModel.allProducts.observe(this, Observer {productList ->
            adapter = ProductsAdapter(productList) { product ->
                onItemClick(product)
            }
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
            binding.rvUsers.adapter = adapter
        })
    }


    private fun onItemClick(product: Product) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_update_product)

        val spinnerOptions: Array<String> = resources.getStringArray(R.array.spinner_options)
        spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerOptions
        )

        // Inicializa los componentes del Dialog
        initDialogComponents(dialog)

        etProductName.setText(product.name)
        etProductPrice.setText(product.price.toString())

        spinner.adapter = spinnerAdapter
        val categoryIndex = when (product.category) {
            ProductCategories.OTROS -> 0
            ProductCategories.TECNOLOGIA -> 1
            ProductCategories.ELECTRODOMESTICOS -> 2
            ProductCategories.DEPORTES -> 3
            ProductCategories.VESTIMENTA -> 4
        }
        spinner.setSelection(categoryIndex)

        initDialogComponentsListeners(product, dialog)

        dialog.show()

    }


    private fun initDialogComponents(dialog: Dialog) {
        etProductName = dialog.findViewById(R.id.et_dialog_name)
        etProductPrice = dialog.findViewById(R.id.et_dialog_price)
        spinner = dialog.findViewById(R.id.spinner_dialog_categories)
        btnUpdate = dialog.findViewById(R.id.btn_dialog_update)
        btnDelete = dialog.findViewById(R.id.btn_dialog_delete)
    }

    private fun initDialogComponentsListeners(product: Product, dialog: Dialog) {
        btnUpdate.setOnClickListener {
            if (isEntryValid(etProductName.text.toString(), etProductPrice.text.toString())) {
                updateOne(
                    product.id,
                    etProductName.text.toString(),
                    etProductPrice.text.toString(),
                    categorySelected
                )
                adapter.notifyDataSetChanged()
                dialog.hide()
            } else {
                toast("Porfavor ingrese valores en los campos")
            }
        }

        btnDelete.setOnClickListener {
            delete(product)
            dialog.hide()
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                categorySelected = when (selectedItem) {
                    "Tecnología" -> ProductCategories.TECNOLOGIA
                    "Electrodomésticos" -> ProductCategories.ELECTRODOMESTICOS
                    "Deportes" -> ProductCategories.DEPORTES
                    "Vestimenta" -> ProductCategories.VESTIMENTA
                    else -> ProductCategories.OTROS // Valor por defecto si no coincide con ninguna opción
                }
                Log.i("ProductListActivity", "categorySelected: $categorySelected")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                categorySelected = ProductCategories.OTROS
            }
        }

    }



    private fun delete(product: Product) {
        viewModel.delete(product)
        adapter.notifyDataSetChanged()
    }

    private fun updateOne(
        productId: Int,
        productName: String,
        productPrice: String,
        productCategory: ProductCategories
    ) {
        viewModel.updateOne(
            productId,
            productName,
            productPrice.toInt(),
            productCategory
        )

    }


    fun isEntryValid(productName: String, productPrice: String): Boolean {
        return if (productName.isBlank() || productPrice.isBlank()) false
        else true
    }


}