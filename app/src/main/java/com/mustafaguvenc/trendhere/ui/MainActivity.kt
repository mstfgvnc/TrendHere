package com.mustafaguvenc.trendhere.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.mustafaguvenc.trendhere.R
import com.mustafaguvenc.trendhere.ui.client.ProductDetails
import com.mustafaguvenc.trendhere.ui.client.ProductListClientDirections
import com.mustafaguvenc.trendhere.ui.seller.ProductListSeller
import com.mustafaguvenc.trendhere.ui.seller.ProductListSellerDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.hide()
    }

    override fun onBackPressed() {
        // super.onBackPressed()
    }



}