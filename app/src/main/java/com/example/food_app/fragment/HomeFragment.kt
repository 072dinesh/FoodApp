package com.example.food_app.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food_app.MainActivity
import com.example.food_app.R
import com.example.food_app.databinding.FragmentHomeBinding
import com.example.food_app.model.resipi
import com.example.food_app.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var toggle: ActionBarDrawerToggle

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
//        shimmerLayout.startShimmer()
        setoverview()
        setobserverss()
        setRecyclerview()
        //setobservers()
        viewmodel.getDataFromAPIso()
        viewmodel.getfood(binding.searchView.query.toString())

        return view


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
        var list=ArrayList<com.example.food_app.model.Result>()
        for(i in list)
        {
            if (i?.title?.lowercase(Locale.ROOT)!!.contains(binding.searchView.query))
            {
                list.add(i)
            }
        }
        if(list.isEmpty()){
            //Toast.makeText(requireContext(),"error",Toast.LENGTH_SHORT).show()

        }
        else
        {
            Timber.e(list.toString())
            adapter.setData(list)
        }
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerview.adapter = adapter

    }



    private fun setoverview(){
        viewmodel.myResponse3.observe(viewLifecycleOwner, Observer {

            it.results?.let { result ->
                //Timber.e("QQQQQQq:",result.toString())
                adapter.setData(result.filterNotNull())

            }
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility=View.GONE

        })
    }


    private fun setobserverss()
    {

        viewmodel.myResponse3.observe(viewLifecycleOwner, Observer {


            it.results?.let { result ->

                var list=ArrayList<com.example.food_app.model.Result>()
                binding.searchView.setOnQueryTextListener(object :OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewmodel.getfood(query.toString())
                        setRecyclerview()

                        //Toast.makeText(requireContext(),"$query",Toast.LENGTH_SHORT).show()
                        return true

                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewmodel.getfood(newText.toString())
                        for(i in result)
                        {
                            if (i?.title?.lowercase(Locale.ROOT)!!.contains(binding.searchView.query))
                            {
                                list.add(i)
                            }
                        }
                        adapter.setData(list)

                        return true
                    }

                })


            }


        })
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerLayout.startShimmer()
    }

    override fun onPause() {

        super.onPause()
        binding.shimmerLayout.stopShimmer()
    }




}