package com.rifalcompany.comebackapps.features.home.data.repository

import com.rifalcompany.comebackapps.features.home.model.PokemonModel
import io.reactivex.Single

interface HomeRepository {
    fun getPokemonRepository(id: Int): Single<PokemonModel>
}