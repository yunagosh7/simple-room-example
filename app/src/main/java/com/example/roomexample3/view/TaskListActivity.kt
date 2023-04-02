package com.example.roomexample3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.roomexample3.database.AppDatabase
import com.example.roomexample3.database.User
import com.example.roomexample3.databinding.ActivityTaskListBinding

class TaskListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskListBinding
    private lateinit var database: AppDatabase

    companion object {
        val EXTRA_FIRSTNAME = "extra_firstname"
        val EXTRA_LASTNAME = "extra_lastname"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val firstName = intent.getStringExtra(EXTRA_FIRSTNAME)
        val lastName = intent.getStringExtra(EXTRA_LASTNAME)

        binding.tvFirstname.text = firstName
        binding.tvLastname.text = lastName




    }
}