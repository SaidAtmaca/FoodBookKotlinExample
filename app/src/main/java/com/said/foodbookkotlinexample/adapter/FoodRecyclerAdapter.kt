package com.said.foodbookkotlinexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.said.foodbookkotlinexample.R
import com.said.foodbookkotlinexample.databinding.RecyclerRowBinding
import com.said.foodbookkotlinexample.model.Food
import com.said.foodbookkotlinexample.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.recycler_row.view.*

class FoodRecyclerAdapter(val foodList: ArrayList<Food>): RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>(),FoodClickListener{

    class FoodViewHolder(var view: RecyclerRowBinding): RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
       // val view = inflater.inflate(R.layout.recycler_row,parent,false)
        val view= DataBindingUtil.inflate<RecyclerRowBinding>(inflater,R.layout.recycler_row,parent,false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.view.food=foodList[position]
        holder.view.listener=this
        /*

         holder.itemView.recyclerFoodName.text=foodList.get(position).foodName
        holder.itemView.recyclerFoodCalory.text=foodList.get(position).foodCalory


        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(foodList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.itemView.recyclerImageView.downloadImage(foodList.get(position).foodImage,
            makePlaceHolder(holder.itemView.context))

         */

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun refreshFoodList(newFoodList: List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun foodClick(view: View) {
        val ruuid= view.foods_uuid.text.toString().toIntOrNull()
        ruuid?.let {
            val action =FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(it)
            Navigation.findNavController(view).navigate(action)
        }
    }


    /*

     */

}