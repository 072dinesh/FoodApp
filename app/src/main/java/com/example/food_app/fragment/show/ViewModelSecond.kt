package com.example.food_app.fragment.show

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_app.model.Foodresipi
import com.example.food_app.repository.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelSecond @Inject constructor(private val repository: RetroRepository): ViewModel() {

    val myResponse2 : MutableLiveData<Response<Foodresipi>> = MutableLiveData()


    fun getPost2(number: Int){
        viewModelScope.launch {

            val responses: Response<Foodresipi> = repository.getPost2(number)

                    myResponse2.value=responses

        }

    }





}