package com.example.roomexample3.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomexample3.R
import com.example.roomexample3.adapters.ProductsAdapter
import com.example.roomexample3.database.AppDatabase
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        val viewModelFactory = ProductListViewModelFactory(database.productDao())
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductListViewModel::class.java)

        viewModel.allProducts.observe(this, Observer {
            val response = it
            adapter = ProductsAdapter(response) { name, price, category ->
                onItemClick(name, price, category)
            }
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
            binding.rvUsers.adapter = adapter
        })

    }


    private fun onItemClick(productName: String, productPrice: Int, productCategory: ProductCategories) {
        toast("Nombre: $productName")
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_update_product)

        val spinnerOptions: Array<String> = resources.getStringArray(R.array.spinner_options)
        spinnerAdapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerOptions
        )

        val etProductName = dialog.findViewById<EditText>(R.id.et_dialog_name)
        val etProductPrice = dialog.findViewById<EditText>(R.id.et_dialog_price)
        val spinner = dialog.findViewById<Spinner>(R.id.spinner_dialog_categories)

        spinner.adapter = spinnerAdapter

        etProductName.setText(productName)
        etProductPrice.setText(productPrice.toString())



        val categoryIndex = when(productCategory) {
            ProductCategories.OTROS -> 0
            ProductCategories.TECNOLOGIA -> 1
            ProductCategories.ELECTRODOMESTICOS -> 2
            ProductCategories.DEPORTES -> 3
            ProductCategories.VESTIMENTA -> 4
        }

        spinner.setSelection(categoryIndex)

        dialog.show()
    }


}