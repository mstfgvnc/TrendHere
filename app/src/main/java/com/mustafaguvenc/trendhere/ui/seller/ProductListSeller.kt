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


class ProductListSeller : Fragment() {


    val viewModel : ProductViewModel by viewModels()
    private val adapter= ProductListAdapter(arrayListOf(),false)
    var productLastId = 0
    var strLastId =""
    var sortedType = ""
    var sortedDirection = ""
    val sortListSeller = arrayListOf<String>("Sıralama Ölçütü","Kategori","Fiyat")


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
        return inflater.inflate(R.layout.fragment_product_list_seller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rcyProductListSeller.layoutManager=LinearLayoutManager(context)
        rcyProductListSeller.adapter=adapter
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
   //    val spinnerAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,sortListSeller)

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinnerCriterion.adapter=spinnerAdapter

/*
        viewModel.getSortingTypes()
        println("burda")

        if(viewModel.sortType.equals("Kategori")){
            spinnerCriterion.setSelection(1)
            println("kategori")
        }else if(viewModel.sortType.equals("Fiyat")){
            spinnerCriterion.setSelection(2)
            println("fiyat")

        }


 */
        spinnerCriterion.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2!=0){
                    sortedType=sortListSeller[p2]
                    viewModel.setSortingTypes(SortedModel(sortedType,sortedDirection))
                    radioGroup.visibility=View.VISIBLE
                }else{

                    radioGroup.visibility=View.INVISIBLE
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
/*
        if(viewModel.sortType.equals("Kategori")){
            spinnerCriterion.setSelection(1)
        }else if(viewModel.sortType.equals("Fiyat")){
            spinnerCriterion.setSelection(2)
        }


 */

    }

    fun observeLiveData(){
        viewModel.productList.observe(viewLifecycleOwner,{
            adapter.updateProductList(it)
        //    spinnerCriterion.setSelection(2)
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
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY)
                } else {
                    tv.setTextColor(Color.BLACK)
                }
                return view
            }

        }
        return spinnerAdapter
    }


}