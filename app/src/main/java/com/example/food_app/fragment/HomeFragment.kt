package com.example.food_app.fragment

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food_app.databinding.FragmentHomeBinding
import com.example.food_app.util.NetworkResult
import com.example.food_app.util.snackBar
import com.example.food_app.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var toggle: ActionBarDrawerToggle

    lateinit var binding: FragmentHomeBinding
//lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private val viewmodel: MainActivityViewModel by viewModels()

    private lateinit var adapter : RecyclerViewAdapter

    @RequiresApi(Build.VERSION_CODES.M)
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
        viewmodel.myResponseIngredian.observe(viewLifecycleOwner, Observer {respon->
//
//            it.results?.let { result ->
//                //Timber.e("QQQQQQq:",result.toString())
//                adapter.setData(result.filterNotNull())
//
//            }
////            binding.shimmerLayout.stopShimmer()
////            binding.shimmerLayout.visibility=View.GONE


            when(respon){
                is NetworkResult.Error -> {
                    respon.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {
                    // show loading indicator or shimmer layout
                    binding.recyclerview.showShimmer() // to start showing shimmer

                    Handler().postDelayed(Runnable {
                        binding.recyclerview.hideShimmer() // to hide shimmer
                    } as Runnable, 3000)

                }
                is NetworkResult.Success -> {
                    respon.data?.results.let {resci->

                        adapter.setData(resci?.filterNotNull()?: emptyList())
                    }
                }
            }

        })



    }


    private fun setobserverss()
    {

        viewmodel.myResponseIngredian.observe(viewLifecycleOwner, Observer {

            it.data?.results?.let { result ->

                var list=ArrayList<com.example.food_app.model.Result>()
                binding.searchView.setOnQueryTextListener(object :OnQueryTextListener{
                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewmodel.getfood(query.toString())
                        setRecyclerview()

                        //Toast.makeText(requireContext(),"$query",Toast.LENGTH_SHORT).show()
                        return true

                    }

                    @RequiresApi(Build.VERSION_CODES.M)
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

//    override fun onResume() {
//        super.onResume()
//        binding.shimmerLayout.startShimmer()
//    }
//
//    override fun onPause() {
//
//        super.onPause()
//        binding.shimmerLayout.stopShimmer()
//    }




}