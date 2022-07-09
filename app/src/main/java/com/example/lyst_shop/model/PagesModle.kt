package com.example.lyst_shop.model

data class PagesModle(
    var id: String,
    var name: String,
    var folwoing: ArrayList<String>?,
    var posts: ArrayList<ProductModle>?,
    var prands: ArrayList<String>?,
    var position: String,
    var description: String,
    var imageUrl : String
)