package com.example.food_app.di

import com.example.food_app.model.Foodresipi
import com.example.food_app.model.resipi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroServieInstance {

    @GET("recipes/complexSearch?apiKey=0ecb521b1fdb46fb832f7a43b7ae6922&number=20")
   suspend fun getDataFromAPI(@Query("query") query: String):Response<resipi>

    @GET("recipes/{id}/information?includeNutrition=true&apiKey=0ecb521b1fdb46fb832f7a43b7ae6922")
    suspend fun getPost2(@Path("id")number:Int):Response<Foodresipi>
//
//    @GET("recipes/complexSearch?apiKey=b6493d639385435a8b419ceaca42b8e4")
//    suspend fun getCustomePost(
//        @Query("query") title:String
//    ):Response<Resipidata>

    @GET("recipes/complexSearch?apiKey=0ecb521b1fdb46fb832f7a43b7ae6922")
    suspend fun getDataFromAPIso():Response<resipi>







}