package com.example.food_app.repository

import com.example.food_app.di.RetroServieInstance
import com.example.food_app.model.Foodresipi
import com.example.food_app.model.resipi
import retrofit2.Response
import javax.inject.Inject

class RetroRepository @Inject constructor(private val retroInstance: RetroServieInstance) {


    suspend fun getDataFromAPI(foodmodel:String):Response<resipi>{
        return retroInstance.getDataFromAPI(foodmodel)
    }

    suspend fun getPost2(id: Int):Response<Foodresipi>{
        return retroInstance.getPost2(id)
    }

    suspend fun getDataFromAPIso():Response<resipi>{
        return retroInstance.getDataFromAPIso()
    }


//    suspend fun getCustomPosts(id:Int):Response<resipi>{
//        return retroInstance.getCustomePost(id)
//    }

}