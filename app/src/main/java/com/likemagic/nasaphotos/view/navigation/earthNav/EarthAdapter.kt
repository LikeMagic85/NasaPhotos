package com.likemagic.nasaphotos.view.navigation.earthNav

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.likemagic.nasaphotos.databinding.FragmentEarthItemBinding
import com.likemagic.nasaphotos.repository.earthDTO.PictureOfEarthDTOItem
import java.text.SimpleDateFormat
import java.util.*

class EarthAdapter(
    private val context:Context,
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
            bind(data[position], context)

        }
    }

    override fun getItemCount() = data.size

    class CityHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(data: PictureOfEarthDTOItem, context: Context){
            val binding = FragmentEarthItemBinding.bind(itemView)
            val date = Calendar.getInstance()
            date.add(Calendar.DATE, -2)
            val fmtY = SimpleDateFormat("yyyy")
            val fmtM = SimpleDateFormat("MM")
            val fmtD = SimpleDateFormat("dd")
            val year = fmtY.format(date.time)
            val month = fmtM.format(date.time)
            val day = fmtD.format(date.time)
            val uri = "https://epic.gsfc.nasa.gov/archive/natural/$year/$month/$day/jpg/${data.image}.jpg"
            binding.earthImage.load(uri)
            binding.dateText.text = data.date
            binding.positionName.text = "${data.centroidCoordinates.lat}   ${data.centroidCoordinates.lon}"
        }
    }
}