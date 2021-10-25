package com.mustafaguvenc.trendhere.ui.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.mustafaguvenc.trendhere.R
import kotlinx.android.synthetic.main.fragment_product_details.*

class ProductDetails : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            productCategory.text="Kategori : " +ProductDetailsArgs.fromBundle(it).category
            productDate.text="Tarih : " +ProductDetailsArgs.fromBundle(it).date
            productId.text="Ürün Kodu : " +ProductDetailsArgs.fromBundle(it).id
            productName.text="Ürün Adı : " +ProductDetailsArgs.fromBundle(it).name
            productPrice.text="Fiyat : " +ProductDetailsArgs.fromBundle(it).price+" TL"
            productExplanation.text="Açıklama : " +ProductDetailsArgs.fromBundle(it).explanation

        }
        btnBack.setOnClickListener {
            val action = ProductDetailsDirections.actionProductDetailsToProductListClient()
            it.findNavController().navigate(action)
        }

    }

}