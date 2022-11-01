package com.rifalcompany.comebackapps.features.home.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonModel(
    @SerializedName("nomor")
    @Expose
    val nomor: String,
    @SerializedName("keterangan")
    @Expose
    val keterangan: String,
//    @SerializedName("data")
//    @Expose
//    val `data`: Data
)