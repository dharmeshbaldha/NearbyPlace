package com.searchnearbylocation.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    fun setPageTitle(title: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = title
        }
    }

}