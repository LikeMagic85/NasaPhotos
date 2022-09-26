package com.likemagic.nasaphotos.view.navigation.solarSystemNav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.likemagic.nasaphotos.databinding.FragmentSolarSystemItemBinding
import com.likemagic.nasaphotos.repository.solarSystem.PhotoOfSolarSystem

class SolarSystemAdapter(
    private var data: List<PhotoOfSolarSystem> = listOf()
):RecyclerView.Adapter<SolarSystemAdapter.CityHolder>() {

    fun setData(data: List<PhotoOfSolarSystem>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = FragmentSolarSystemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        with(holder){
            bind(data[position])

        }
    }

    override fun getItemCount() = data.size

    class CityHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(data: PhotoOfSolarSystem){
            val binding = FragmentSolarSystemItemBinding.bind(itemView)
            binding.marsImage.load(data.url)
        }
    }
}