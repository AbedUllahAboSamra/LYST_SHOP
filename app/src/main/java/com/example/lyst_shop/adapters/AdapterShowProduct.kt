package com.example.lyst_shop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lyst_shop.databinding.ProductDesignBinding
import com.example.lyst_shop.model.ProductModle
import com.squareup.picasso.Picasso

class AdapterShowProduct(var arr: ArrayList<ProductModle>) :
    RecyclerView.Adapter<AdapterShowProduct.viewHolder>() {

    class viewHolder(var binding: ProductDesignBinding) : RecyclerView.ViewHolder(binding.root)

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        context = parent.context
        var binding = ProductDesignBinding.inflate(LayoutInflater.from(parent.context))
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var post = arr[position]
        holder.binding.tvPrice.text = post.price.toString()
        holder.binding.tvPriceAfterDiscount.text = post.discount.toString()
        holder.binding.tvBrand.text = post.brand
        holder.binding.tvAntherText.text = post.text
        Picasso.get().load(post.imageUrl).into(holder.binding.imgProduct)
    }
    override fun getItemCount(): Int {
        return arr.size
    }
}