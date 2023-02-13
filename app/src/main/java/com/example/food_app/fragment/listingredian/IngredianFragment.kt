package com.example.food_app.fragment.listingredian

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_app.R
import com.example.food_app.databinding.*
import com.example.food_app.fragment.HomeFragmentDirections
import com.example.food_app.fragment.show.ViewAdapter
import com.example.food_app.fragment.show.ViewDetailsArgs
import com.example.food_app.fragment.show.ViewDetailsDirections
import com.example.food_app.fragment.show.ViewModelSecond
import com.example.food_app.util.NetworkResult
import com.example.food_app.util.snackBar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.fragment_ingredian.*

@AndroidEntryPoint
class IngredianFragment : Fragment() {
    private var _binding: FragmentIngredianBinding? = null
    private val binding get() = _binding!!
    //private var _binding: FragmentHomeBinding?= null


    private val viewmodelsecode: ViewModelSecond by viewModels()
    private lateinit var adapter: IngredianAdepter
    private val args by navArgs<IngredianFragmentArgs>()

    @RequiresApi(Build.VERSION_CODES.M)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIngredianBinding.inflate(inflater, container, false)

        setingredients()
        setupUi()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupUi() {

        args.seedata.let {

            viewmodelsecode.getPost2(it)

        }
        adapter = IngredianAdepter()
        binding.recyclerviewmain.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerviewmain.adapter = adapter

    }

    private fun setingredients() {
        viewmodelsecode.myResponseView.observe(viewLifecycleOwner, Observer { response ->


//                response.data?.let { recipe ->
//
//                    recipe.extendedIngredients.let { ingredients ->
//                        adapter.setData(ingredients?.filterNotNull() ?: emptyList())
//                    }
//                    binding.shimmerLayouts.stopShimmer()
//                    binding.shimmerLayouts.visibility=View.GONE
//
//                }

            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {
                    // show loading indicator or shimmer layout
                    binding.recyclerviewmain.showShimmer() // to start showing shimmer

                    Handler().postDelayed(Runnable {
                        binding.recyclerviewmain.hideShimmer() // to hide shimmer
                    } as Runnable, 3000)

                }
                is NetworkResult.Success -> {
                    response.data?.extendedIngredients.let { resci ->

                        adapter.setData(resci?.filterNotNull() ?: emptyList())
                    }
                }
            }


        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}