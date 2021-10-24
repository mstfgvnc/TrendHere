package com.mustafaguvenc.trendhere.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mustafaguvenc.trendhere.BaseViewModel
import com.mustafaguvenc.trendhere.model.ProductModel
import com.mustafaguvenc.trendhere.model.SortedModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor
    (application : Application): BaseViewModel(application){

    var database = FirebaseDatabase.getInstance().reference
    val productList = MutableLiveData<List<ProductModel>>()
    val productLastId= MutableLiveData<Int>()
    var sortType=""
    var sortDirection=""

        fun getData(){
        launch {
            val getProducts = object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val products = arrayListOf<ProductModel>()
                    var id = 1

                    for( i in snapshot.children){
                        if(i.key.equals("sort")){
                            sortType=i.child("sortType").value.toString()
                            sortDirection=i.child("sortDirection").value.toString()

                        }else{
                            products.add(
                                ProductModel(i.child("name").value.toString(),
                                    i.child("category").value.toString(),
                                    i.child("price").value.toString(),
                                    i.child("explanation").value.toString(),
                                    i.child("date").value.toString())
                            )
                            id =  i.key.toString().toInt() + 1
                        }

                    }

                    if(sortType.equals("Kategori") && sortDirection.equals("up")){
                        productList.value=products.sortedBy { it.category }
                    }else if(sortType.equals("Kategori") && sortDirection.equals("down")){
                        productList.value=products.sortedByDescending { it.category }
                    } else if(sortType.equals("Fiyat") && sortDirection.equals("up")){
                        productList.value=products.sortedBy { it.price }
                    }else if(sortType.equals("Fiyat") && sortDirection.equals("down")){
                        productList.value=products.sortedByDescending { it.price }
                    }else{
                        productList.value=products
                    }
                    productLastId.value=id
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }

            database.addValueEventListener(getProducts)
            database.addListenerForSingleValueEvent(getProducts)
        }

        }

        fun setDataInFirebase(productId:String,product: ProductModel){
            launch {
                database.child(productId).setValue(product)
            }
        }

        fun setSortingTypes(sortedModel: SortedModel){
            launch {
                database.child("sort").setValue(sortedModel)
                getData()

            }

        }

        fun getSortingTypes(){
            launch {
                val getSortingType = object  : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                       for(i in snapshot.children){
                           if(i.key.equals("sort")){
                               sortType=i.child("sortType").value.toString()
                               sortDirection=i.child("sortDirection").value.toString()
                               println(sortType)
                           }
                       }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                }
              //  database.addValueEventListener(getSortingType)
                database.addListenerForSingleValueEvent(getSortingType)
            }

        }


    }