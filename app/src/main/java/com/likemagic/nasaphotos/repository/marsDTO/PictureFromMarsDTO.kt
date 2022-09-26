package com.likemagic.nasaphotos.repository.marsDTO


import com.google.gson.annotations.SerializedName

data class PictureFromMarsDTO(
    @SerializedName("photos")
    val photos: List<Photo>
)
