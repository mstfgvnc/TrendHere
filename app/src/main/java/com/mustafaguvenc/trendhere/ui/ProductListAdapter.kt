package com.mustafaguvenc.trendhere.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mustafaguvenc.trendhere.R
import com.mustafaguvenc.trendhere.databinding.ItemProductBinding
import com.mustafaguvenc.trendhere.model.ProductModel

class ProductListAdapter(var productList :  List<ProductModel>,var isSeller:Boolean)
    : RecyclerView.Adapter<ProductListAdapter.ProductListItemViewHolder>(),ProductClickListener{

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
     //   holder.view.productLayout.setBackgroundColor(Color.rgb(118,181,197)) //rgb(118,181,197)


        if(position%2==0){
            holder.view.productLayout.setBackgroundColor(Color.rgb(118,181,197)) //rgb(118,181,197)
        }else if(position%2==1){
            holder.view.productLayout.setBackgroundColor(Color.rgb(200,208,197)) // rgb(234,182,118)
        }



    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateProductList(newProductList:List<ProductModel>){

        productList=newProductList
      //  productList.clear()
      //  productList.addAll(newProductList)
        notifyDataSetChanged()

    }

    override fun onProductClicked(v: View) {
        TODO("Not yet implemented")
    }


}