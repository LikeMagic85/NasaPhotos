package com.likemagic.nasaphotos.repository.solarSystem


    fun getAllPhotos():List<PhotoOfSolarSystem>{
        return listOf(
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/PIA12365/PIA12365~small.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/PIA16889/PIA16889~medium.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/PIA10119/PIA10119~small.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/PIA21428/PIA21428~medium.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/PIA18657/PIA18657~orig.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/PIA17998/PIA17998~medium.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/PIA13115/PIA13115~medium.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/ACD17-0168-009/ACD17-0168-009~medium.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/iss052e004913/iss052e004913~medium.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e000963/GSFC_20171208_Archive_e000963~small.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e000962/GSFC_20171208_Archive_e000962~medium.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/hubble-captures-vivid-auroras-in-jupiters-atmosphere_28000029525_o/hubble-captures-vivid-auroras-in-jupiters-atmosphere_28000029525_o~medium.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001662/GSFC_20171208_Archive_e001662~small.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e000759/GSFC_20171208_Archive_e000759~medium.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e000976/GSFC_20171208_Archive_e000976~small.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/PIA18152/PIA18152~small.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/KSC-20160908-PH_SRJ04_0015/KSC-20160908-PH_SRJ04_0015~medium.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/PIA20692/PIA20692~small.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/PIA22099/PIA22099~small.jpg"),
            PhotoOfSolarSystem("https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001550/GSFC_20171208_Archive_e001550~small.jpg"),
        )
    }


data class PhotoOfSolarSystem(var url: String)