package com.said.foodbookkotlinexample.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.said.foodbookkotlinexample.model.Food
import com.said.foodbookkotlinexample.service.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application):BaseViewModel(application) {

    val foodLiveData=MutableLiveData<Food>()



    fun getRoomData(uuid: Int){

        launch {
            val dao= FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData.value=food
        }
    }
}