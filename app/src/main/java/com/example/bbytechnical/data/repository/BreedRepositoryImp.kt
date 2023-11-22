package com.example.bbytechnical.data.repository

import com.example.bbytechnical.data.remote.NetworkDataSource
import com.example.bbytechnical.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *
 *  BBYTechnical
 *  Created by venkatakalluri on 11/21/23.
 *
 *  Implementation of the [BreedRepository] interface that interacts with the network data source.
 *
 *   This class provides methods to fetch dog images and retrieve a list of all dog breeds.
 *   It relies on [networkDataSource] to perform the actual data fetching operations.
 *
 *   @property networkDataSource The data source responsible for fetching dog-related data from the network.
 *
 *   @constructor Creates a [BreedRepositoryImp] with the specified [networkDataSource].
 */

class BreedRepositoryImp @Inject constructor(private val networkDataSource: NetworkDataSource) :
    BreedRepository {
    override suspend fun getDogImages(breedType: String): Flow<NetworkResult<List<String>>> {
        return networkDataSource.getDogImages(breedType)
    }

    override suspend fun getAllBreeds(): Flow<NetworkResult<List<String>>> {
        return networkDataSource.getAllBreeds()
    }
}
