package com.searchnearbylocation.ui.nearbyDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.searchnearbylocation.R
import com.searchnearbylocation.data.api.model.Result
import com.searchnearbylocation.data.model.NearbyPlacePhotoModel
import com.searchnearbylocation.databinding.FragmentNearbyLocationDetailBinding
import com.searchnearbylocation.ui.nearbyList.NearbyPlaceViewModel
import com.searchnearbylocation.utils.BaseFragment
import com.searchnearbylocation.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NearbyPlaceDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentNearbyLocationDetailBinding
    private val viewModel by viewModels<NearbyPlaceViewModel>()
    private lateinit var dataResult: Result

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNearbyLocationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeArgumentData()

        fetchPlacePhoto()

        bindObserver()

        setArgumentDataInView(dataResult)

    }

    private fun initializeArgumentData() {
        val argumentData = arguments?.getString("data")

        // convert Json data to Model object.
        dataResult = Gson().fromJson(argumentData, Result::class.java)

        // Set Title
        setPageTitle(dataResult.name)
    }

    private fun fetchPlacePhoto() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getNearbyPlacePhoto(dataResult.fsq_id)
        }
    }

    private fun bindObserver() {
        viewModel.nearbyPlacePhotoData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    setPlacePhotoInView(it.data)
                }

                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setArgumentDataInView(data: Result) {

        val lat = data.geocodes.main.latitude
        val lng = data.geocodes.main.longitude

        binding.apply {
            tvTitleValue.text = data.name
            tvStatusValue.text = data.closed_bucket
            tvDistanceValue.text = getString(R.string.distance_in_meters, data.distance.toString())
            tvAddressValue.text = data.location.formatted_address

            btnGetDirection.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:$lat,$lng")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
    }

    private fun setPlacePhotoInView(data: NearbyPlacePhotoModel?) {
        if (data?.isNotEmpty() == true) {
            val imageSize = "800x600"
            val imageData = data[0].let {
                return@let "${it.prefix}$imageSize${it.suffix}"
            }

            binding.icon.apply {
                Glide.with(context)
                    .load(imageData)
                    .apply(
                        RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.error_place_holder)
                    )
                    .into(this)
            }
        } else {
            binding.icon.apply {
                Glide.with(context)
                    .load(R.drawable.error_place_holder)
                    .fitCenter()
                    .into(this)
            }
        }
    }

}