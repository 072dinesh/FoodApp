package com.example.food_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_app.model.resipi

import com.example.food_app.repository.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: RetroRepository):ViewModel() {
   // lateinit var liveDataList: MutableLiveData<List<GridViewData>>
   val myResponse3 :MutableLiveData<resipi> = MutableLiveData()

    val myResponse2 :MutableLiveData<resipi> = MutableLiveData()
    val myResponse4 :MutableLiveData<resipi> = MutableLiveData()


    init {
        getfood()
    }


    private fun getfood() {
        viewModelScope.launch {
            val response: Response<resipi> =repository.getDataFromAPI("chinese")
            if (response.isSuccessful){
                response.body()?.let {
                    myResponse3.value=it
                    // myResponse3.value.results
                    //myAdepter.setData(it)
                }
            }

        }
    }


//    fun getPost2(number: Int){
//        viewModelScope.launch {
//
//            val responses:Response<resipi> = repository.getPost2(number)
//            if (responses.isSuccessful){
//                responses.body()?.let {
//                    myResponse2.value=it
//
//                }
//            }
//
//        }
//
//    }



//    fun getCustomPosts(id:Int)
//    {
//        viewModelScope.launch {
//            val response:Response<resipi> =repository.getCustomPosts(id)
//            if (response.isSuccessful){
//                response.body()?.let {
//                    myResponse3.value=it
//                    // myResponse3.value.results
//                    //myAdepter.setData(it)
//                }
//            }
//
//        }
//
//    }




}