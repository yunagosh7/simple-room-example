package com.example.roomexample3.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE product_name LIKE :name")
    fun getByName(name: String): Flow<Product>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOne(product: Product)

    @Delete
    fun delete(product: Product)

    @Update
    fun update(product: Product)
}