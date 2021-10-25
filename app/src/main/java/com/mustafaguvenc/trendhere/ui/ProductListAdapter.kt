package com.mustafaguvenc.trendhere.ui

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mustafaguvenc.trendhere.R
import com.mustafaguvenc.trendhere.databinding.ItemProductBinding
import com.mustafaguvenc.trendhere.model.ProductModel
import com.mustafaguvenc.trendhere.ui.client.ProductListClientDirections
import com.mustafaguvenc.trendhere.ui.seller.ProductListSellerDirections
import kotlinx.android.synthetic.main.item_product.view.*

class ProductListAdapter(var productList :  List<ProductModel>,var isSeller:Boolean)
    : RecyclerView.Adapter<ProductListAdapter.ProductListItemViewHolder>(),ProductClickListener{

    val hashMapProduct:HashMap<Int,ProductModel> = hashMapOf()

    class ProductListItemViewHolder(var view : ItemProductBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemProductBinding>(inflater,
            R.layout.item_product,parent,false)
        return ProductListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListItemViewHolder, position: Int) {

        holder.view.product = productList[position]
        holder.view.listener=this
        hashMapProduct.put(productList[position].id!!.toInt(),productList[position])

        if(position%2==0){
            holder.view.productLayout.setBackgroundColor(Color.rgb(118,181,197))
        }else if(position%2==1){
            holder.view.productLayout.setBackgroundColor(Color.rgb(200,208,197))
        }


    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateProductList(newProductList:List<ProductModel>){

        productList=newProductList
        notifyDataSetChanged()

    }


    override fun onProductClicked(v: View) {
        val product=hashMapProduct.get(v.productId.text.toString().toInt())
        if(isSeller){
            val action = ProductListSellerDirections.actionProductListSellerToProductAddAndEdit(
                product!!.id,
                product.name,
                product.explanation,
                product.category,
                product.price.toString()
            )
                v.findNavController().navigate(action)

        }else{
            val action = ProductListClientDirections.actionProductListClientToProductDetails(
                product?.id!!,
                product.name!!,
                product.category!!,
                product.explanation!!,
                product.date!!,
                product.price.toString())
            v.findNavController().navigate(action)

        }
    }


}