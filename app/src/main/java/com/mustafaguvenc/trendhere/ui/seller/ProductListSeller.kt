package com.mustafaguvenc.trendhere.ui.seller

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafaguvenc.trendhere.R
import com.mustafaguvenc.trendhere.ui.ProductListAdapter
import com.mustafaguvenc.trendhere.ui.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product_list_seller.*
import android.widget.TextView
import android.widget.Toast
import com.mustafaguvenc.trendhere.model.ProductModel
import com.mustafaguvenc.trendhere.model.SortedModel
import android.app.ListActivity

import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.android.synthetic.main.item_product.view.*


class ProductListSeller : Fragment() {


    val viewModel : ProductViewModel by viewModels()
    private val adapter= ProductListAdapter(arrayListOf(),true)
    var strLastId =""
    var sortedType = ""
    var sortedDirection = ""
    val sortListSeller = arrayListOf<String>("Sıralama Ölçütü","Kategori","Fiyat")
    var isFirstInitialize = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list_seller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rcyProductListSeller.layoutManager=LinearLayoutManager(context)
        rcyProductListSeller.adapter=adapter

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rcyProductListSeller)

        viewModel.getData()

        btnSellerEntry.setOnClickListener {
            val action = ProductListSellerDirections.actionProductListSellerToProductAddAndEdit(
                strLastId,
                "",
                "",
                "",
                "")
            it.findNavController().navigate(action)
        }

        btnBack.setOnClickListener{
            val action = ProductListSellerDirections.actionProductListSellerToProductListClient()
            it.findNavController().navigate(action)
        }

        val spinnerAdapter=createSpinnerAdapter()
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinnerCriterion.adapter=spinnerAdapter

        spinnerCriterion.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               if(isFirstInitialize){
                   isFirstInitialize=false

                   }else{
                   if(p2!=0){
                       sortedType=sortListSeller[p2]
                       viewModel.setSortingTypes(SortedModel(sortedType,sortedDirection))
                       radioGroup.visibility=View.VISIBLE
                   }else{

                       radioGroup.visibility=View.INVISIBLE
                        }
                   }

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        upSorting.setOnClickListener {
            sortedDirection="up"
            viewModel.setSortingTypes(SortedModel(sortedType,sortedDirection))
        }

        downSorting.setOnClickListener {
            sortedDirection="down"
            viewModel.setSortingTypes(SortedModel(sortedType,sortedDirection))

        }

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.productList.observe(viewLifecycleOwner,{

            adapter.updateProductList(it)

            sortedDirection=viewModel.sortDirection
            sortedType=viewModel.sortType
            if(sortedDirection.equals("up")){
                radioGroup.check(upSorting.id)
            }else if(sortedDirection.equals("down")){
                radioGroup.check(downSorting.id)
            }
            if(sortedType.equals("Kategori")){
                spinnerCriterion.setSelection(1)
            }else if(sortedType.equals("Fiyat")){
                spinnerCriterion.setSelection(2)
            }


        })
        viewModel.productLastId.observe(viewLifecycleOwner,{
            strLastId = it.toString()
        })
    }

    fun createSpinnerAdapter():ArrayAdapter<String>{

        val spinnerAdapter = object : ArrayAdapter<String>(requireContext(),R.layout.spinner_item,sortListSeller){

            override fun isEnabled(position: Int): Boolean {
                return true

            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view=  super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(Color.GRAY)
                } else {
                    tv.setTextColor(Color.BLACK)
                }
                return view
            }

        }
        return spinnerAdapter
    }

    var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

          viewModel.deleteProduct(viewHolder.itemView.productId.text.toString())

        }
    }

}