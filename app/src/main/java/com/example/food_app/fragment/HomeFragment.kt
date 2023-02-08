package com.example.food_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_app.databinding.FragmentHomeBinding
import com.example.food_app.model.resipi
import com.example.food_app.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
//lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private val viewmodel: MainActivityViewModel by viewModels()

    private lateinit var adapter : RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        var view = binding.root

        setRecyclerview()
        setobservers()
        return view


    }

    private fun setobservers()
    {
        viewmodel.myResponse3.observe(viewLifecycleOwner, Observer {

            it.results?.let { result ->
                adapter.setData(result.filterNotNull())
            }

        })
    }

    private fun setRecyclerview() {
        adapter = RecyclerViewAdapter(
            onMainClick = {
               // it.id.toString()
                it.id?.let {
                    //Toast.makeText(requireContext(), "${it.id.toString()}", Toast.LENGTH_LONG).show()
                    var action =HomeFragmentDirections.actionHomeFragmentToViewDetails(it)
                    findNavController().navigate(action)
                }

            },

        )
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerview.adapter = adapter

    }




}