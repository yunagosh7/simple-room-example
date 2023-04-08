package com.example.roomexample3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample3.R
import com.example.roomexample3.database.Product
import com.example.roomexample3.model.ProductCategories

class ProductsAdapter(
    private var productList: List<Product> = emptyList(),
    val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(productList[position], onItemClick)
    }


}