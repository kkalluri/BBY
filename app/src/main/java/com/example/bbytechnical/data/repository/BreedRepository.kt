package com.example.bbytechnical.data.repository

import com.example.bbytechnical.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow
/**
 *
 * BBYTechnical
 * Created by venkatakalluri on 11/21/23.
 */
interface BreedRepository {
    suspend fun getDogImages(breedType: String): Flow<NetworkResult<List<String>>>
    suspend fun getAllBreeds(): Flow<NetworkResult<List<String>>>
}
