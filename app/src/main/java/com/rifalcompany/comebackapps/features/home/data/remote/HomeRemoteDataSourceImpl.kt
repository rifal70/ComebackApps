package com.rifalcompany.comebackapps.features.home.data.remote

import com.rifalcompany.comebackapps.features.home.data.service.HomeService
import com.rifalcompany.comebackapps.features.home.model.PokemonModel
import io.reactivex.Single
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(private val service: HomeService) :
    HomeRemoteDataSource {
    override fun getPokemonRemote(id: Int): Single<PokemonModel> {
        return service.getPokemonService(id)
    }
}