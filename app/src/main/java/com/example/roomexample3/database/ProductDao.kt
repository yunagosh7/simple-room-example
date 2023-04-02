package com.example.roomexample3.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAll(): List<Product>

    @Query("SELECT * FROM products WHERE product_name LIKE :name")
    fun getByName(name: String): Product

    @Insert
    fun insertOne(product: Product)

    @Delete
    fun delete(product: Product)
}