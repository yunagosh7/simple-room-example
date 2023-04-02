package com.example.roomexample3.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample3.database.User
import com.example.roomexample3.databinding.ItemUserBinding

class UsersViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    private val binding = ItemUserBinding.bind(view)


    fun bind(user: User) {
        binding.tvName.text = user.name
        binding.tvLastName.text = user.lastName
    }


}