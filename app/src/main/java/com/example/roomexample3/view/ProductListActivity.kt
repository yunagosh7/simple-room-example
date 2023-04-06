package com.example.roomexample3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomexample3.adapters.UsersAdapter
import com.example.roomexample3.database.AppDatabase
import com.example.roomexample3.databinding.ActivityTaskListBinding
import com.example.roomexample3.viewmodel.ProductListViewModel
import com.example.roomexample3.viewmodel.ProductListViewModelFactory
import com.example.roomexample3.viewmodel.ProductViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskListBinding
    private lateinit var database: AppDatabase
    private lateinit var viewModel: ProductListViewModel

    private lateinit var adapter: UsersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        val viewModelFactory = ProductListViewModelFactory(database.productDao())
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductListViewModel::class.java)



        viewModel.allProducts.observe(this, Observer {
            val response = it
            adapter = UsersAdapter(response)
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
            binding.rvUsers.adapter = adapter
        })


        initUI()

    }

    private fun initUI() {

    }


}