package com.example.roomexample3.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomexample3.database.Product
import com.example.roomexample3.database.ProductDao
import com.example.roomexample3.model.ProductCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsViewModel(private val productDao: ProductDao) : ViewModel() {

    // Inserta un item usando el DAO
    private fun insertOne(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            productDao.insertOne(product)
        }
    }

    // Recibe los datos y devuelve un producto
    private fun getNewItemEntry(productName: String, price: Int, category: ProductCategories): Product {
        return Product(
            name = productName,
            price = price,
            category = category
        )
    }

    // Crea el item y lo inserta en la DB
    fun addNewItem(productName: String, productPrice: String, productCategory: ProductCategories) {
        val newProduct = getNewItemEntry(productName, productPrice.toInt(), productCategory)
        insertOne(newProduct)
    }
}

class ProductViewModelFactory(private val productDao: ProductDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductsViewModel(productDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}