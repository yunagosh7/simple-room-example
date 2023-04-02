package com.example.roomexample3.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @ColumnInfo(name = "first_name") val name: String,
    @PrimaryKey @ColumnInfo(name = "last_name") val lastName: String
        )