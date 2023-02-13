package com.example.food_app.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.databinding.ItemFoodBinding
import com.example.food_app.model.Result
import com.example.food_app.util.DiffUtilExt

class RecyclerViewAdapter(val onMainClick: (Result) -> Unit) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    //private var listData : List<GridViewData>?=null
    private var callList = emptyList<Result>()

    class MyViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            currentItem: Result,
            onMainClick: (Result) -> Unit
//            onAlbumClick: (resipi) -> Unit
        ) {
            binding.foodclick = currentItem
            binding.root.setOnClickListener {
                onMainClick(currentItem)
            }


        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFoodBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }


    override fun getItemCount(): Int {
        return callList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = callList.getOrNull(position)

        currentItem?.let {
            holder.bind(it, onMainClick)
        }

    }

    fun setData(Result: List<Result>) {
//        this.callList= user
//        notifyDataSetChanged()

        val userDiffUtil = DiffUtilExt(callList, Result)
        val userDiffUtilResult = DiffUtil.calculateDiff(userDiffUtil)
        callList = Result
        userDiffUtilResult.dispatchUpdatesTo(this)

    }


}