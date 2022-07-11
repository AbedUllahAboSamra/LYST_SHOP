package com.example.lyst_shop.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.lyst_shop.MainActivity
import com.example.lyst_shop.databinding.ActivitySplachBinding
import com.example.lyst_shop.db.MyDataBase
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
        var db = MyDataBase(this)
        var a = db.addId("SDasd")
        Log.e("ASD",a.toString()+"ASDDDDSAAAA")

        getPages()
    }

    fun getPages() {
        FirebaseFirestore.getInstance()
            .collection("pages")
            .get()
            .addOnSuccessListener { pages ->
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
                                var post = ProductModle(
                                    id = a.id,
                                    name = a.getString("name").toString(),
                                    brand = a.getString("brand").toString(),
                                    text = a.getString("text").toString(),
                                    discribtion = a.getString("discribtion").toString(),
                                    price = a.getDouble("price")?.toDouble() ?: 0.0,
                                    discount = a.getDouble("discount")?.toDouble() ?: 0.0,
                                    likes = null,
                                    posterId = a.getString("posterId").toString(),
                                    posterName = a.getString("posterName").toString(),
                                    posterImageL = a.getString("posterImageL")
                                        .toString(),
                                    imageUrl = a.getString("imageUrl")
                                        .toString()
                                )

                                posts.add(post)


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
            .addOnFailureListener {
                Log.e("ASD", it.toString() + "ASD FAIL")
            }
    }
}