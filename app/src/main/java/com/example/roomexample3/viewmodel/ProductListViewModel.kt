package com.example.roomexample3.viewmodel

import androidx.lifecycle.*
import com.example.roomexample3.database.Product
import com.example.roomexample3.database.ProductDao
import kotlinx.coroutines.launch

class ProductListViewModel(private val productDao: ProductDao) : ViewModel() {

    var allProducts: LiveData<List<Product>> = productDao.getAll().asLiveData()




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
