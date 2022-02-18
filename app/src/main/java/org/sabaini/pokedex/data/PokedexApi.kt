package org.sabaini.pokedex.data

import retrofit2.http.GET

interface PokedexApi {

    @GET("")
    fun fetchPokedex(): List<PokedexResponse>
}