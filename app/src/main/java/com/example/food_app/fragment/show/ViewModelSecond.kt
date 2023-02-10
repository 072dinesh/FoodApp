package com.example.food_app.fragment.show

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_app.R
import com.example.food_app.model.Foodresipi
import com.example.food_app.model.resipi
import com.example.food_app.repository.RetroRepository
import com.example.food_app.util.BaseViewModel
import com.example.food_app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelSecond @Inject constructor(private val repository: RetroRepository,application: Application): BaseViewModel(application) {

    val myResponseView : MutableLiveData<NetworkResult<Foodresipi>> = MutableLiveData()
    var mContext = application

//    fun getPost2(number: Int){
//        viewModelScope.launch {
//
//            val responses: Response<Foodresipi> = repository.getPost2(number)
//
//            myResponseView.value=responses
//
//        }
//
//    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun getPost2(id:Int){

        viewModelScope.launch {
            myResponseView.value = NetworkResult.Loading()

            if (isConnected()){


                val response5 : Response<Foodresipi> = repository.getPost2(id)
                myResponseView.value = handleResponse(response5)


            }
            else {
                myResponseView.value = NetworkResult.Error(
                    mContext.getString(R.string.no_internet)
                )
            }


        }
    }





}