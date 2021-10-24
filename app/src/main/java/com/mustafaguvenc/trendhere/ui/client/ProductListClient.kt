package com.mustafaguvenc.trendhere.ui.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafaguvenc.trendhere.R
import com.mustafaguvenc.trendhere.ui.ProductViewModel
import com.mustafaguvenc.trendhere.ui.ProductListAdapter
import kotlinx.android.synthetic.main.fragment_product_list_client.*

class ProductListClient : Fragment() {

    val viewModel : ProductViewModel by viewModels()
    private val adapter= ProductListAdapter(arrayListOf(),false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list_client, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcyProductListClient.layoutManager=LinearLayoutManager(context)
        rcyProductListClient.adapter=adapter
        viewModel.getData()

        btnSeller.setOnClickListener {
            val action = ProductListClientDirections.actionProductListClientToProductListSeller()
            it.findNavController().navigate(action)
        }

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.productList.observe(viewLifecycleOwner,{
            adapter.updateProductList(it)
        })
    }

}