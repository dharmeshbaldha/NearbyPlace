package com.searchnearbylocation.ui.nearbyList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.searchnearbylocation.R
import com.searchnearbylocation.data.api.model.NearbyPlaceModel
import com.searchnearbylocation.data.api.model.Result
import com.searchnearbylocation.databinding.RawNearbyLocationListBinding

class NearbyPlaceListAdapter(private val nearbyLocationList: NearbyPlaceModel, private val onListItemClick: (Result) -> Unit) :
    RecyclerView
    .Adapter<NearbyPlaceListAdapter
.NearbyLocationListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyLocationListHolder {
        val view = RawNearbyLocationListBinding.inflate(LayoutInflater.from(parent.context))
        return NearbyLocationListHolder(view)
    }

    override fun getItemCount() = nearbyLocationList.results.size

    override fun onBindViewHolder(holder: NearbyLocationListHolder, position: Int) {
        holder.bind(nearbyLocationList.results[position])
    }


    inner class NearbyLocationListHolder(private val binding: RawNearbyLocationListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Result) {

            binding.apply {
                tvTitle.text = data.name
                tvIsOpen.text = data.closed_bucket
                tvDistance.apply {
                    text = context.getString(R.string.distance, data.distance.toString())
                }

                val iconUrlData = data.categories[0].icon.let {
                    return@let binding.icon.context.getString(R.string.icon_list_url, it.prefix, it.suffix)
                }

                Glide.with(icon.context)
                    .load(iconUrlData)
                    .fitCenter()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(icon)

                root.setOnClickListener {
                    onListItemClick(data)
                }
            }
        }
    }
}