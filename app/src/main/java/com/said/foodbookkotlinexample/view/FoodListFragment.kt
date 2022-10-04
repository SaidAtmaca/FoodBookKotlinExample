package com.said.foodbookkotlinexample.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.said.foodbookkotlinexample.R
import com.said.foodbookkotlinexample.adapter.FoodRecyclerAdapter
import com.said.foodbookkotlinexample.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*

class FoodListFragment : Fragment() {

    private lateinit var viewModel:FoodListViewModel
    private val recyclerViewAdapter = FoodRecyclerAdapter(arrayListOf())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProvider(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()

        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=recyclerViewAdapter

        swipeRefreshLayout.setOnRefreshListener{
            foodProgressBar.visibility=View.VISIBLE
            foodErrorMessage.visibility=View.GONE
            recyclerView.visibility=View.GONE
            swipeRefreshLayout.isRefreshing=false
            viewModel.refreshFromInternet()
        }

        observeLiveData()



    }

    fun observeLiveData(){

        viewModel.foods.observe(viewLifecycleOwner, Observer {foods ->
            foods?.let {
                recyclerView.visibility=View.VISIBLE
                recyclerViewAdapter.refreshFoodList(foods)
            }
        })

        viewModel.foodErrorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let{
                if(it){
                    foodErrorMessage.visibility=View.VISIBLE
                    recyclerView.visibility=View.GONE
                }else{
                    foodErrorMessage.visibility=View.GONE
                }
            }
        })

        viewModel.foodIsLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if(it){
                    recyclerView.visibility=View.VISIBLE
                    foodErrorMessage.visibility=View.GONE
                    foodProgressBar.visibility=View.VISIBLE
                }else{
                    foodProgressBar.visibility=View.GONE
                }
            }
        })
    }


}