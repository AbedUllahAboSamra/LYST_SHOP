package com.example.lyst_shop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lyst_shop.databinding.DesignFromMainPageBinding
import com.example.lyst_shop.model.PagesModle
import com.example.lyst_shop.model.ProductModle
import com.squareup.picasso.Picasso

class AdapterRecMainPage(var arr: ArrayList<PagesModle>) :
    RecyclerView.Adapter<AdapterRecMainPage.myViewHolder>() {
    class myViewHolder(var binding: DesignFromMainPageBinding) :
        RecyclerView.ViewHolder(binding.root)

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        context = parent.context
        var binding = DesignFromMainPageBinding.inflate(LayoutInflater.from(parent.context))
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.binding.shoopName.text = arr[position].name
        holder.binding.shoopName2.text = arr[position].name

        Picasso.get().load(arr[position].imageUrl).into(holder.binding.imvShopImageView)
        if (arr[position].posts?.size != 0) {
            Picasso.get().load(arr[position].posts!![arr[position].posts!!.size - 1].imageUrl)
                .into(holder.binding.imagePost)
        }

        if (position % 5 == 0) {
            holder.binding.titleText.visibility = View.GONE
            holder.binding.titleImage.visibility = View.VISIBLE
        } else {
            holder.binding.titleText.visibility = View.VISIBLE
            holder.binding.titleImage.visibility = View.GONE
        }

        holder.binding.recProduct.layoutManager = GridLayoutManager(context, 2)
        holder.binding.recProduct.adapter = AdapterShowProduct(ArrayList())

    }

    override fun getItemCount(): Int {
        return 5
    }

}