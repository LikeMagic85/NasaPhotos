package com.likemagic.nasaphotos.view.navigation.earthNav


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.likemagic.nasaphotos.databinding.FragmentEarthItemBinding
import com.likemagic.nasaphotos.repository.earthDTO.PictureOfEarthDTOItem

class EarthAdapter(

    private var data: List<PictureOfEarthDTOItem> = listOf()
):RecyclerView.Adapter<EarthAdapter.CityHolder>() {

    fun setData(data: List<PictureOfEarthDTOItem>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = FragmentEarthItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        with(holder){
            bind(data[position])

        }
    }

    override fun getItemCount() = data.size

    class CityHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(data: PictureOfEarthDTOItem){
            val binding = FragmentEarthItemBinding.bind(itemView)
            val date = data.date
            val year = date.substring(0..3)
            val month = date.substring(5..6)
            val day = date.substring(8..9)
            val uri = "https://epic.gsfc.nasa.gov/archive/natural/$year/$month/$day/jpg/${data.image}.jpg"
            binding.earthImage.load(uri)
            binding.dateText.text = data.date
            binding.positionName.text = "${data.centroidCoordinates.lat}   ${data.centroidCoordinates.lon}"
        }
    }
}