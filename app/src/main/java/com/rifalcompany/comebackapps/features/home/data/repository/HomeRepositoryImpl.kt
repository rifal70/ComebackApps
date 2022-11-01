package com.rifalcompany.comebackapps.features.home.data.repository

import com.rifalcompany.comebackapps.features.home.data.remote.HomeRemoteDataSource
import com.rifalcompany.comebackapps.features.home.model.PokemonModel
import io.reactivex.Single
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val remoteDataSource: HomeRemoteDataSource) :
    HomeRepository {

    override fun getPokemonRepository(id: Int): Single<PokemonModel> {
        return remoteDataSource.getPokemonRemote(id)
    }
}