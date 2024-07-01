package com.searchnearbylocation.ui.searchNearbyPlace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.searchnearbylocation.R
import com.searchnearbylocation.databinding.FragmentSearchNearbyPlaceBinding
import com.searchnearbylocation.utils.BaseFragment
import com.searchnearbylocation.utils.hideKeyBoard

class SearchNearbyPlaceFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchNearbyPlaceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNearbyPlaceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        // Search Button Event
        binding.btnSearchPlace.apply {
            setOnClickListener {
                if (text.isNotEmpty()) {
                    if (text.isNotEmpty()) {
                        val bundle = Bundle().apply {
                            putString("data", text.toString())
                        }
                        findNavController().navigate(R.id.action_searchNearbyPlaceFragment_to_nearbyLocationListFragment, bundle)
                    } else
                        Toast.makeText(requireContext(), getString(R.string.enter_place_name), Toast.LENGTH_SHORT).show()
                }
            }
        }

        // IME Action Search Button Event
        binding.etSearchPlace.apply {
            setOnEditorActionListener { _, actionId, _ ->
                hideKeyBoard(requireActivity())

                if (actionId == IME_ACTION_SEARCH && text.isNotEmpty()) {
                    val bundle = Bundle().apply {
                        putString("data", text.toString())
                    }
                    findNavController().navigate(R.id.action_searchNearbyPlaceFragment_to_nearbyLocationListFragment, bundle)
                } else
                    Toast.makeText(requireContext(), getString(R.string.enter_place_name), Toast.LENGTH_SHORT).show()
                true
            }
        }
    }

}