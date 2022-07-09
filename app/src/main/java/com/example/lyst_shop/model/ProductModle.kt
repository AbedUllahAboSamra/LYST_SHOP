package com.example.lyst_shop.model

data class ProductModle(
    var id: String,
    var name: String,
    var brand: String,
    var text: String,
    var discribtion: String,
    var imageUrl:String,
    var price: Double,
    var discount: Double = 0.0,
    var likes: ArrayList<String>?,
    var posterId: String,
    var posterName: String,
    var posterImageL: String
    )