package com.example.roomexample3.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.roomexample3.database.AppDatabase
import com.example.roomexample3.database.User
import com.example.roomexample3.databinding.ActivityMainBinding
import com.example.roomexample3.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)


        binding.btnAddUser.setOnClickListener {
            val firstName = binding.etFirstname.text.toString()
            val lastName = binding.etLastname.text.toString()
            addUser(firstName, lastName)
        }

        binding.btnNavigate.setOnClickListener {
            navigateToTasksActivity()
        }


    }


    private fun addUser(firstName: String, lastName: String) {
        if(validateFields()) {
            CoroutineScope(Dispatchers.IO).launch {
                database.userDao().insertOne(User(firstName, lastName))
                runOnUiThread {
                    resetFields()
                    toast("Anduvo")
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        if(binding.etFirstname.text.isBlank() || binding.etLastname.text.isBlank() ) {
            toast("Porfavor ingrese datos en los campos")
             return false
        }
        return true
    }

    private fun resetFields() {
        binding.etFirstname.text.clear()
        binding.etLastname.text.clear()

    }

    private fun navigateToTasksActivity() {
        val intent = Intent(this, TaskListActivity::class.java)
        startActivity(intent)
    }



}