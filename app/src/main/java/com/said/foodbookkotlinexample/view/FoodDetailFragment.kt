package com.said.foodbookkotlinexample.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.said.foodbookkotlinexample.R
import com.said.foodbookkotlinexample.databinding.FragmentFoodDetailBinding
import com.said.foodbookkotlinexample.util.downloadImage
import com.said.foodbookkotlinexample.util.makePlaceHolder
import com.said.foodbookkotlinexample.viewmodel.FoodDetailViewModel
import com.said.foodbookkotlinexample.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_detail.*

class FoodDetailFragment : Fragment() {

    private lateinit var viewModel:FoodDetailViewModel


    private var besinId=0
    private lateinit var dataBinding: FragmentFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_food_detail,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            besinId=FoodDetailFragmentArgs.fromBundle(it).besinId
            println(besinId)
        }


        viewModel= ViewModelProvider(this).get(FoodDetailViewModel::class.java)
        viewModel.getRoomData(besinId)


        observeLiveData()


    }

    fun observeLiveData(){
        viewModel.foodLiveData.observe(viewLifecycleOwner, Observer { food ->
            food?.let {

                dataBinding.choosenFood=it

            /*     foodName.text=it.foodName
                foodCalory.text=it.foodCalory
                foodCarb.text=it.foodCarb
                foodProtein.text=it.foodProtein
                foodOil.text=it.foodOil
                context?.let {
                    foodImage.downloadImage(food.foodImage, makePlaceHolder(it))
                }
*/
            }
        })
    }


}