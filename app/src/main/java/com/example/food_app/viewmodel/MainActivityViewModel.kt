package com.example.food_app.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_app.R
import com.example.food_app.di.RetroServieInstance
import com.example.food_app.model.resipi

import com.example.food_app.repository.RetroRepository
import com.example.food_app.util.BaseViewModel
import com.example.food_app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: RetroRepository,application: Application):BaseViewModel(application) {
   // lateinit var liveDataList: MutableLiveData<List<GridViewData>>
   val myResponseIngredian :MutableLiveData<NetworkResult<resipi>> = MutableLiveData()

    val myResponse2 :MutableLiveData<resipi> = MutableLiveData()
    val myResponse4 :MutableLiveData<resipi> = MutableLiveData()

    var mContext = application
    private val _allUsers = MutableLiveData<NetworkResult<resipi>>()
    val allUsers: LiveData<NetworkResult<resipi>>
        get() = _allUsers



    init {
        getDataFromAPIso()

    }

//     fun getfood(foodmodel:String) {
//        viewModelScope.launch {
//            val response: Response<resipi> =repository.getDataFromAPI(foodmodel)
//            if (response.isSuccessful){
//                response.body()?.let {
//                    myResponseIngredian.value=it
//                    // myResponse3.value.results
//                    //myAdepter.setData(it)
//                }
//            }
//
//        }
//    }




    @RequiresApi(Build.VERSION_CODES.M)
    fun getfood(query:String){

        viewModelScope.launch {
            myResponseIngredian.value = NetworkResult.Loading()

            if (isConnected()){


                val response4 : Response<resipi> = repository.getDataFromAPI(query)
                myResponseIngredian.value = handleResponse(response4)


            }
            else {
                myResponseIngredian.value = NetworkResult.Error(
                    mContext.getString(R.string.no_internet)
                )
            }


        }
    }

//    fun getDataFromAPIso() {
//        viewModelScope.launch {
//            val response: Response<resipi> =repository.getDataFromAPIso()
//            if (response.isSuccessful){
//                response.body()?.let {
//                    myResponseIngredian.value=it
//                    // myResponse3.value.results
//                    //myAdepter.setData(it)
//                }
//            }
//
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.M)


    fun getDataFromAPIso(){

        viewModelScope.launch {
            myResponseIngredian.value = NetworkResult.Loading()

            if (isConnected()){


                val response4 : Response<resipi> = repository.getDataFromAPIso()
                myResponseIngredian.value = handleResponse(response4)


            }
            else {
                myResponseIngredian.value = NetworkResult.Error(
                    mContext.getString(R.string.no_internet)
                )
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