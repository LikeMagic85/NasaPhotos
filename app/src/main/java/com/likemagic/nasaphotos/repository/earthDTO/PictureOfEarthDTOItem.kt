package com.likemagic.nasaphotos.repository.earthDTO


import com.google.gson.annotations.SerializedName

data class PictureOfEarthDTOItem(
    @SerializedName("centroid_coordinates")
    val centroidCoordinates: CentroidCoordinates,
    @SerializedName("date")
    val date: String,
    @SerializedName("image")
    val image: String,
)