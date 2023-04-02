package com.example.roomexample3.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomexample3.model.ProductCategories

@Entity(tableName = "products")
data class Product (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "product_name") val name: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "category") val category: ProductCategories
        )