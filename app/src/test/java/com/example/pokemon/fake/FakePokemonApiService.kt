package com.example.pokemon.fake

import com.example.pokemon.model.QueryResponse
import com.example.pokemon.network.PokemonApiService
import retrofit2.Response

class FakePokemonApiService : PokemonApiService {

    private var responseToReturn: Response<QueryResponse>? = null

    fun setResponse(response: Response<QueryResponse>) {
        this.responseToReturn = response
    }

    override suspend fun getPokemonResults(limit: Int): Response<QueryResponse> {
        return responseToReturn ?: throw IllegalStateException("No response configured!")
    }
}