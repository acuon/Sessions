package dev.acuon.sessions.data.api

import com.google.gson.JsonObject
import dev.acuon.sessions.ui.model.image.BreedImageAll
import dev.acuon.sessions.ui.model.image.BreedImageSingle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Response<JsonObject>

    @GET("breed/{breed_name}/images/random")
    suspend fun getSingleImage(@Path("breed_name") breed_name: String): Response<BreedImageSingle>

    @GET("breed/{breed_name}/images")
    suspend fun getAllImages(@Path("breed_name") breed_name: String): Response<BreedImageAll>
}