package com.said.foodbookkotlinexample.service

import com.said.foodbookkotlinexample.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodApi {

    //GET, POST
    //https://github.com/atilsamancioglu/BTK20-JSONVeriSeti/blob/master/besinler.json

    //BASE_URL ->https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getFood(): Single<List<Food>>
}