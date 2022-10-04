package com.said.foodbookkotlinexample.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.said.foodbookkotlinexample.model.Food
import com.said.foodbookkotlinexample.service.FoodAPIService
import com.said.foodbookkotlinexample.service.FoodDatabase
import com.said.foodbookkotlinexample.util.SpecialSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application): BaseViewModel(application) {

    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage= MutableLiveData<Boolean>()
    val foodIsLoading= MutableLiveData<Boolean>()
    private var updateTime= 10*60*1000*1000*1000L


    private val foodApiService=FoodAPIService()
    private val disposable=CompositeDisposable()
    private val specialSharedPreferences= SpecialSharedPreferences(getApplication())


    fun refreshData(){

        val savingTime = specialSharedPreferences.takeTime()
        if(savingTime != null && savingTime !=0L && System.nanoTime()-savingTime <updateTime){
            //sqlite tan al
            getDatafromSQlite()
        }else{
            getDataFromInternet()
        }



    }

    fun refreshFromInternet(){
        getDataFromInternet()
    }

    private fun getDatafromSQlite(){

        foodIsLoading.value=true
        launch {
            Toast.makeText(getApplication(),"Besinleri Room'dan aldık",Toast.LENGTH_LONG).show()
           val foodList= FoodDatabase(getApplication()).foodDao().getAllFood()
            showFoods(foodList)

        }

    }
    private fun getDataFromInternet(){
        foodIsLoading.value=true


        disposable.add(
            foodApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        Toast.makeText(getApplication(),"Besinleri Internet'ten aldık",Toast.LENGTH_LONG).show()

                        hideSqlite(t)
                    }

                    override fun onError(e: Throwable) {
                        foodErrorMessage.value=true
                        foodIsLoading.value=false
                        e.printStackTrace()
                    }

                })
        )
    }
    private fun showFoods(foodsList: List<Food>){

        foods.value=foodsList
        foodErrorMessage.value=false
        foodIsLoading.value=false
    }

    private fun hideSqlite(foodList: List<Food>){
        launch {
            val dao= FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
            val uuidList= dao.insertAll(*foodList.toTypedArray())
            var i=0

            while(i<foodList.size){
                foodList[i].uuid=uuidList[i].toInt()
                i=i+1
            }
            showFoods(foodList)

        }

        specialSharedPreferences.saveTime(System.nanoTime())
    }

}