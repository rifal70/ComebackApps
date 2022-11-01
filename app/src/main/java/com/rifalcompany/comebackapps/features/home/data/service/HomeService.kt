package com.rifalcompany.comebackapps.features.home.data.service

import com.rifalcompany.comebackapps.features.home.model.PokemonModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {
    @GET("data/{id}")
    fun getPokemonService(@Path("id") id: Int): Single<PokemonModel>
}