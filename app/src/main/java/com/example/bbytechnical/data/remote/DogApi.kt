package com.example.bbytechnical.data.remote

import com.example.bbytechnical.data.model.DogBreedsResponse
import com.example.bbytechnical.data.model.DogImagesResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * BBYTechnical
 * Created by venkatakalluri on 11/21/23.
 */
interface DogApi {
    @GET("breed/{breed}/images")
    suspend fun getDogImages(@Path("breed") breed: String):DogImagesResponse

    @GET("breeds/list/all")
    suspend fun getAllBreeds(): DogBreedsResponse

    companion object {
        const val BASE_URL: String = "https://dog.ceo/api/"
    }
}

