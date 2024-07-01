package com.searchnearbylocation.ui.nearbyList

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.searchnearbylocation.R
import com.searchnearbylocation.data.api.model.Result
import com.searchnearbylocation.databinding.FragmentNearbyLocationListBinding
import com.searchnearbylocation.utils.BaseFragment
import com.searchnearbylocation.utils.NetworkResult
import com.searchnearbylocation.utils.showPermissionDialog
import com.searchnearbylocation.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NearbyPlaceListFragment : BaseFragment() {

    private lateinit var binding: FragmentNearbyLocationListBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel by viewModels<NearbyPlaceViewModel>()
    private var searchArgumentData: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        askPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNearbyLocationListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObjectInitialization()

        bindObserver()

        initializeArgumentData()

        searchArgumentData?.let { fetchLatLong(it) }

    }

    private fun initializeArgumentData() {
        searchArgumentData = arguments?.getString("data")
    }

    private fun setObjectInitialization() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private fun bindObserver() {
        viewModel.locationListLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is NetworkResult.Success -> {

                    if (it.data?.results?.isNotEmpty() == true) {
                        binding.nearbyLocationListView.apply {
                            isVisible = true
                            adapter = NearbyPlaceListAdapter(it.data, ::onListItemClick)
                        }
                    } else {
                        binding.emptyView.isVisible = true
                        binding.nearbyLocationListView.isVisible = false
                    }

                }

                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    /**
     * This method is used to proceed item click event of NearBy Places event.
     */
    private fun onListItemClick(data: Result) {
        val jsonData = Gson().toJson(data)
        val bundle = Bundle().apply {
            putString("data", jsonData)
        }

        findNavController().navigate(R.id.action_nearbyLocationListFragment_to_nearbyLocationDetailFragment, bundle)
    }

    /**
     * Ask for Location permission and proceed once permission granted.
     */
    private fun askPermission() {
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->

            // Permission Granted.
            if (result[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                searchArgumentData?.let { fetchLatLong(it) }

            } else { // Permission Denied.
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {

                    showPermissionDialog(
                        requireActivity(),
                        getString(R.string.dialog_title_grant_permission), getString(R.string.dialog_message_require_permission)
                    )

                } else {
                    showSnackBar(requireView(), getString(R.string.snackbar_message_denied_require_permission), true)
                }
            }
        }

        requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    @SuppressLint("MissingPermission")
    private fun fetchLatLong(placeSearchQuery: String) {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            if (it != null) {

                val options = HashMap<String, String>()
                options["query"] = placeSearchQuery
                options["ll"] = "${it.latitude},${it.longitude}"
                options["radius"] = "5000"

                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getNearbyLocation(options)
                }
            } else {
                showSnackBar(requireView(), getString(R.string.general_error_message_something_went_wrong))
            }
        }
    }
}