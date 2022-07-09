package com.example.lyst_shop.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.lyst_shop.MainActivity
import com.example.lyst_shop.databinding.ActivitySplachBinding
import com.example.lyst_shop.model.PagesModle
import com.example.lyst_shop.model.ProductModle
import com.google.firebase.firestore.FirebaseFirestore

class SplachActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplachBinding


    companion object {
        var pagesArray = ArrayList<PagesModle>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplachBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPages()
    }

    fun getPages() {
        FirebaseFirestore.getInstance()
            .collection("pages")
            .get()
            .addOnSuccessListener { pages ->
                Log.e("aasd", pages.size().toString() + "pages")
                var index = 0
                for (page in pages) {
                    var following = ArrayList<String>()
                    FirebaseFirestore.getInstance()
                        .collection("pages")
                        .document(page.id)
                        .collection("folwoing")
                        .get()
                        .addOnSuccessListener { followings ->
                            for (a in followings) {
                                following.add(a.id)
                            }
                        }
                    var posts = ArrayList<ProductModle>()
                    FirebaseFirestore.getInstance()
                        .collection("pages")
                        .document(page.id)
                        .collection("posts")
                        .get()
                        .addOnSuccessListener { postsIds ->
                            for (a in postsIds) {
                                FirebaseFirestore.getInstance()
                                    .collection("posts")
                                    .document(a.id)
                                    .get()
                                    .addOnSuccessListener { apost ->
                                        var post = ProductModle(
                                            id = a.id,
                                            name = apost.getString("name").toString(),
                                            brand = apost.getString("brand").toString(),
                                            text = apost.getString("text").toString(),
                                            discribtion = apost.getString("discribtion").toString(),
                                            price = apost.getDouble("price")!!.toDouble(),
                                            discount = apost.getDouble("discount")!!.toDouble()
                                                ?: 0.0,
                                            likes = null,
                                            posterId = apost.getString("discribtion").toString(),
                                            posterName = apost.getString("posterName").toString(),
                                            posterImageL = apost.getString("posterImageL")
                                                .toString(),
                                            imageUrl = apost.getString("imageUrl")
                                                .toString()
                                        )

                                        posts.add(post)
                                    }


                            }
                        }

                    var pageModle = PagesModle(
                        id = page.id,
                        name = page.getString("name").toString(),
                        position = page.getString("position").toString(),
                        description = page.getString("description").toString(),
                        folwoing = following,
                        posts = posts,
                        prands = null,
                        imageUrl = page.getString("imageUrl").toString()

                    )
                    index++
                    pagesArray.add(pageModle)
                    if (index == pages.size()) {
                        Handler().postDelayed({
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }, 1500)
                    }

                }


            }
            .addOnFailureListener {}
    }
}