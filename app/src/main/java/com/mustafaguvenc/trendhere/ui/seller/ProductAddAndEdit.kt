package com.mustafaguvenc.trendhere.ui.seller

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.firebase.database.FirebaseDatabase
import com.mustafaguvenc.trendhere.R
import com.mustafaguvenc.trendhere.model.ProductModel
import com.mustafaguvenc.trendhere.ui.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product_add_and_edit.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ProductAddAndEdit : Fragment() {

    var productId : String? = ""
    var productName :String? = ""
    var productExplanation :String?= ""
    var productPrice :String?= ""
    var productCategory:String? = ""
    var productDate:String? = ""

    val viewModel : ProductViewModel by viewModels()

   // var database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId =ProductAddAndEditArgs.fromBundle(it).id
            productName =ProductAddAndEditArgs.fromBundle(it).name
            productCategory =ProductAddAndEditArgs.fromBundle(it).category
            productExplanation =ProductAddAndEditArgs.fromBundle(it).explanation
            productPrice =ProductAddAndEditArgs.fromBundle(it).price

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_add_and_edit, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentDateTime = LocalDateTime.now()
        productDate=currentDateTime.format(DateTimeFormatter.ISO_DATE)

        etProductDate.setText(productDate)
        etProductCategory.setText(productCategory)
        etProductId.setText(productId)
        etProductExplanation.setText(productExplanation)
        etProductName.setText(productCategory)
        etProductPrice.setText(productPrice)

        etProductDate.isEnabled  = false
        etProductId.isEnabled = false

        btnProductEnter.setOnClickListener {

            if(etProductName.text.toString().equals("")){

                Toast.makeText(context,"Lütfen Ürün Adını Giriniz...", Toast.LENGTH_SHORT).show()

            }else if(etProductCategory.text.toString().equals("")){

                Toast.makeText(context,"Lütfen Ürün Kategorisini Giriniz...", Toast.LENGTH_SHORT).show()

            }else if(etProductPrice.text.toString().equals("")){

                Toast.makeText(context,"Lütfen Ürün Fiyatını Giriniz...", Toast.LENGTH_SHORT).show()

            }else{
                productCategory=etProductCategory.text.toString()
                productExplanation=etProductExplanation.text.toString()
                productName=etProductName.text.toString()
                productPrice=etProductPrice.text.toString()

              //  database.child(productId.toString()).setValue(ProductModel(productName,productCategory,productPrice,productExplanation,productDate))
                viewModel.setDataInFirebase(productId.toString(),ProductModel(productName,productCategory,productPrice,productExplanation,productDate))
                val action = ProductAddAndEditDirections.actionProductAddAndEditToProductListSeller()
                it.findNavController().navigate(action)
            }


        }

        btnQuit.setOnClickListener {
            val action = ProductAddAndEditDirections.actionProductAddAndEditToProductListSeller()
            it.findNavController().navigate(action)
        }




    }

}