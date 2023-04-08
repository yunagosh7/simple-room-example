package com.example.roomexample3.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.roomexample3.database.Product
import com.example.roomexample3.database.ProductDao
import com.example.roomexample3.model.ProductCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(private val productDao: ProductDao) : ViewModel() {

    var allProducts: LiveData<List<Product>> = productDao.getAll().asLiveData()


    fun updateOne(
        productID: Int,
        productName: String,
        productPrice: Int,
        productCategory: ProductCategories
    ) {
        Log.i("ProductListViewModel", "productCategory 2 $productCategory")
        val newProduct = getNewItemEntry(productID, productName, productPrice, productCategory)
        CoroutineScope(Dispatchers.IO).launch {
            productDao.update(newProduct)

        }
        Log.i("ProductListViewModel", "${newProduct.id} y ${newProduct.name}")
    }

    private fun getNewItemEntry(
        productID: Int,
        productName: String,
        productPrice: Int,
        productCategory: ProductCategories
    ): Product {
        return Product(
            id = productID,
            name = productName,
            price = productPrice,
            category = productCategory
        )
    }


    fun delete(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            productDao.delete(product)
        }
    }



}


class ProductListViewModelFactory(private val productDao: ProductDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(productDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
