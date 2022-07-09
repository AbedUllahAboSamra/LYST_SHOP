package com.example.lyst_shop.model

data class AccountModel(
    var id: String,
    var name: String,
    var imageUri: String,
    var bio: String?,
    var description: String?,
    var numberOfFollower: String?,
    var numberOfRecipes: String?,
    var accountType: String = "user",

)