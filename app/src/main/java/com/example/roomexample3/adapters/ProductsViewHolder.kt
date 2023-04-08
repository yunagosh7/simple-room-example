package com.example.roomexample3.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample3.database.Product
import com.example.roomexample3.databinding.ItemUserBinding
import com.example.roomexample3.model.ProductCategories

class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    private val binding = ItemUserBinding.bind(view)


    fun bind(product: Product, onItemClick: (Product) -> Unit ) {
        binding.tvName.text = product.name
        binding.tvPrice.text = product.price.toString()

        val category = when(product.category) {
            ProductCategories.OTROS -> "Otros"
            ProductCategories.TECNOLOGIA -> "Tecnología"
            ProductCategories.ELECTRODOMESTICOS -> "Electrodomésticos"
            ProductCategories.DEPORTES -> "Deportes"
            ProductCategories.VESTIMENTA -> "Vestimenta"
        }

        binding.tvCategory.text = category

        binding.ibEdit.setOnClickListener {
            onItemClick(product)
        }

    }



}