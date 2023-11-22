package com.example.bbytechnical.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbytechnical.data.remote.NetworkResult
import com.example.bbytechnical.data.repository.BreedRepositoryImp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * BBYTechnical
 * Created by venkatakalluri on 11/21/23.
 */
class DogBreedViewModel @Inject constructor(val repository: BreedRepositoryImp) : ViewModel(){

    private val _breeds = MutableStateFlow<NetworkResult<List<String>>>(
        NetworkResult.Loading(false)
    )
    val breeds = _breeds.asStateFlow()

    private val _dogImages = MutableStateFlow<NetworkResult<List<String>>>(
        NetworkResult.Loading(false)
    )
    val dogImages = _dogImages.asStateFlow()

    fun fetchDogBreeds() {
        viewModelScope.launch {
            repository.getAllBreeds().collect { networkResult ->
                _breeds.value = networkResult
            }
        }
    }

    fun fetchDogImages(breedType: String) {
        viewModelScope.launch {
            repository.getDogImages(breedType).collect { networkResult ->
                _dogImages.value = networkResult
            }
        }
    }

}
