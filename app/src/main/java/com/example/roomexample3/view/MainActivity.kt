package com.example.roomexample3.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.roomexample3.R
import com.example.roomexample3.database.AppDatabase
import com.example.roomexample3.database.Product
import com.example.roomexample3.databinding.ActivityMainBinding
import com.example.roomexample3.model.ProductCategories
import com.example.roomexample3.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase

    private lateinit var categorySelected: ProductCategories


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)


       binding.btnAddUser.setOnClickListener {
            val name = binding.etName.text.toString()
            val price = binding.etPrice.text.toString().toInt()
            addProduct(name, price, categorySelected)
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

        binding.btnNavigate.setOnClickListener {
            navigateToTasksActivity()
        }

        initUI()

    }

    private fun initUI() {
        val spinnerOptions: Array<String> = resources.getStringArray(R.array.spinner_options)
        val spinnerAdapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerOptions
        )
        binding.spinnerCategories.adapter = spinnerAdapter

    }


    private fun addProduct(name: String, price: Int, category: ProductCategories) {
        if(validateFields()) {
            CoroutineScope(Dispatchers.IO).launch {
                database.userDao().insertOne(Product(name = name, price = price, category = category))
                runOnUiThread {
                    resetFields()
                    toast("Anduvo")
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        if(binding.etName.text.isBlank() || binding.etPrice.text.isBlank() ) {
            toast("Porfavor ingrese datos en los campos")
             return false
        }
        return true
    }

    private fun resetFields() {
        binding.etName.text.clear()
        binding.etPrice.text.clear()
    }

    private fun navigateToTasksActivity() {
        val intent = Intent(this, TaskListActivity::class.java)
        startActivity(intent)
    }



}