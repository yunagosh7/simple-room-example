package com.example.roomexample3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roomexample3.adapters.UsersAdapter
import com.example.roomexample3.database.AppDatabase
import com.example.roomexample3.databinding.ActivityTaskListBinding

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskListBinding
    private lateinit var database: AppDatabase

    private lateinit var adapter: UsersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)

       //getUserList()
        initUI()

    }

    private fun initUI() {

    }

    /*private fun getUserList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = database.userDao().getAll()
            runOnUiThread {
                adapter = UsersAdapter(response)
                binding.rvUsers.layoutManager = LinearLayoutManager(this@TaskListActivity)
                binding.rvUsers.adapter = adapter
            }
        }

    }*/


}