package com.example.food_app.fragment.show

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_app.R
import com.example.food_app.databinding.FragmentViewDetailsBinding
import com.example.food_app.util.NetworkResult
import com.example.food_app.util.snackBar
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat


@AndroidEntryPoint
class ViewDetails : Fragment() {

    //lateinit var binding: FragmentViewDetailsBinding

    private var _binding: FragmentViewDetailsBinding? = null
    private val binding get() = _binding!!


    private val viewmodelsecode: ViewModelSecond by viewModels()
    private lateinit var adapter: ViewAdapter
    private val args by navArgs<ViewDetailsArgs>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        _binding = FragmentViewDetailsBinding.inflate(inflater, container, false)


        setupUi()
        settupoverview()


        binding.backbtn.setOnClickListener {

            findNavController().navigateUp()

        }


        return binding.root

    }


    private fun settupoverview() {


        viewmodelsecode.myResponseView.observe(viewLifecycleOwner, Observer { resp ->

            when (resp) {
                is NetworkResult.Error -> {
                    resp.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {
                    // show loading indicator or shimmer layout
                }
                is NetworkResult.Success -> {


                    var def = DecimalFormat("#.##")

                    resp.data.let {
                        binding.showdata = it

                        it?.nutrition?.nutrients?.forEach {
                            //Log.d("mainssssss","${it.amount}")
                            var pers = it.amount!! * it.percentOfDailyNeeds!! / 100
                            if (it.name == "Sugar") {
                                // Timber.e(it.amount.toString())
                                binding.progreesBar.progress = it.amount!!.toInt()
                                binding.textViewProgress.text = it.amount!!.toString()
                                binding.tvCarbUnit.text = it.unit.toString()
                                binding.tvCarbPer.text =
                                    this.resources.getString(R.string.pers_text, def.format(pers))

                            } else if (it.name == "Fat") {
                                binding.progressBarFat.progress = it.amount!!.toInt()
                                binding.textViewProgress2.text = it.amount!!.toString()
                                binding.tvFatUnit.text = it.unit.toString()
                                binding.tvFatPer.text =
                                    this.resources.getString(R.string.pers_text, def.format(pers))
                            } else if (it.name == "Protein") {
                                binding.progressBarProtien.progress = it.amount!!.toInt()
                                binding.textViewProgress3.text = it.amount!!.toString()
                                binding.tvProUnit.text = it.unit.toString()
                                binding.tvProPer.text =
                                    this.resources.getString(R.string.pers_text, def.format(pers))

                            } else if (it.name == "Calories") {
                                binding.progressBarCal.progress = it.amount!!.toInt()
                                binding.textViewProgress4.text = it.amount!!.toString()
                                binding.tvCalUnit.text = it.unit.toString()

                            } else {
                                //Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                            }

                        }
                        val rating: Float = it?.healthScore!!.toFloat() * 5 / 100
                        binding.rating.rating = rating


                        var url = it?.sourceUrl
                        binding.tvSeeDetailRecipe.setOnClickListener {
                            it?.let {
                                val uri = Uri.parse(url)
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                startActivity(intent)
                            }
                        }

                        it?.id?.let { it ->
                            binding.tvSeeDetailRecipes.setOnClickListener { res ->
                                var action =
                                    ViewDetailsDirections.actionViewDetailsToIngredianFragment(it)
                                findNavController().navigate(action)
                            }
                        }
                        resp.data.let {
                            binding.showdata = it
                            it?.extendedIngredients.let {
                                adapter.setData(it!!.filterNotNull())
                            }

                        }

                    }
                }

            }


        })

    }


//    private fun setingredients()
//    {
//        viewmodelsecode.myResponseView.observe(viewLifecycleOwner, Observer {
//
//
//            it.let {
//
//                it.data.let {
//                    binding.showdata = it
//                    it?.extendedIngredients.let {
//                        adapter.setData(it!!.filterNotNull())
//                    }
//
//                }
//            }
//
//        })
//
//    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupUi() {

        args.viewdatas.let {

            viewmodelsecode.getPost2(it)

        }
        binding.backbtn.setOnClickListener {
            findNavController().navigateUp()
        }

        adapter = ViewAdapter()
        binding.res.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.res.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

