package com.rifalcompany.comebackapps.features.home.data.remote

import com.rifalcompany.comebackapps.features.home.model.PokemonModel
import io.reactivex.Single
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeRemoteDataSource {
    fun getPokemonRemote(
        id: Int
    ): Single<PokemonModel>
}