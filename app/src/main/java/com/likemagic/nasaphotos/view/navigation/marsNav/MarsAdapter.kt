package com.likemagic.nasaphotos.view.navigation.marsNav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.likemagic.nasaphotos.databinding.FragmentMarsItemBinding
import com.likemagic.nasaphotos.repository.marsDTO.Camera
import com.likemagic.nasaphotos.repository.marsDTO.Photo
import com.likemagic.nasaphotos.repository.marsDTO.Rover

class MarsAdapter(private var data: List<Photo> = listOf()):RecyclerView.Adapter<MarsAdapter.CityHolder>() {

    fun setData(data: List<Photo>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = FragmentMarsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        with(holder){
            bind(data[position], data[position].camera, data[position].rover)

        }
    }

    override fun getItemCount() = data.size

    class CityHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(photo: Photo, camera: Camera, rover: Rover,){
            val binding = FragmentMarsItemBinding.bind(itemView)
            binding.marsImage.load(photo.imgSrc)
            binding.cameraName.text = camera.fullName
            binding.roverName.text = rover.name
            binding.solNumber.text = photo.sol.toString()
        }
    }
}