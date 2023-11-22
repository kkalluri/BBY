package com.example.bbytechnical.data.remote

import android.util.Log
import com.example.bbytechnical.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 *
 *  BBYTechnical
 *  Created by venkatakalluri on 11/21/23.
 *
 *  DataSource responsible for fetching dog-related data from a remote API using [dogApi].
 *   This class provides methods to retrieve a list of all dog breeds and to fetch dog images
 *   for a specific breed type.
 *
 *   @property dogApi The API service used to interact with the dog-related endpoints.
 *   @property dispatcher The coroutine dispatcher for network operations, provided by dependency injection.
 */
class NetworkDataSource @Inject constructor(
    private val dogApi: DogApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {


    fun getDogImages(breedType: String): Flow<NetworkResult<List<String>>> = flow {
        try {
            emit(NetworkResult.Loading(true))

            val response = dogApi.getDogImages(breedType)

            Log.d("BreedDataSource getDogImages", "response $response")
            emit(NetworkResult.Success(response.message))
        } catch (e: Exception) {
            // Handle the network error and emit a Failure
            emit(NetworkResult.Failure("Failed to fetch Dog Images"))
        }
    }.flowOn(dispatcher)

    fun getAllBreeds(): Flow<NetworkResult<List<String>>> = flow {
        try {
            emit(NetworkResult.Loading(true))

            val response = dogApi.getAllBreeds()

            Log.d("BreedDataSource getAllBreeds", "response $response")
            emit(NetworkResult.Success(response.message.keys.toList()))
        } catch (e: Exception) {
            // Handle the network error and emit a Failure
            emit(NetworkResult.Failure("Failed to fetch Dogs Data"))
        }
    }.flowOn(dispatcher)

}
