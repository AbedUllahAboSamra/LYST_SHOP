package com.example.lyst_shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lyst_shop.adapters.AdapterRecMainPage
import com.example.lyst_shop.adapters.AdapterShowProduct
import com.example.lyst_shop.databinding.ActivityMainBinding
import com.example.lyst_shop.screen.SplachActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recMainPage.layoutManager = LinearLayoutManager(this)
        binding.recMainPage.adapter = AdapterRecMainPage(SplachActivity.pagesArray)
    }
}