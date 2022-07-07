package dev.acuon.sessions.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import dev.acuon.sessions.data.api.ApiService
import dev.acuon.sessions.ui.model.image.BreedImageAll
import dev.acuon.sessions.ui.model.image.BreedImageSingle


class MainRepository(
    private val apiService: ApiService
) {
    private val breedList = MutableLiveData<List<String>>()
    private val selectedBreedSingleImage = MutableLiveData<BreedImageSingle>()
    private val selectedBreedAllImages = MutableLiveData<BreedImageAll>()
    private val listOfBreeds by lazy { ArrayList<String>() }

    val allBreeds: LiveData<List<String>>
        get() = breedList

    val breedImage: LiveData<BreedImageSingle>
        get() = selectedBreedSingleImage

    val breedImages: LiveData<BreedImageAll>
        get() = selectedBreedAllImages

    suspend fun getAllBreeds() {
        val result = apiService.getAllBreeds()
        if (result.body() != null) {
            val message: JsonObject = result.body()!!.getAsJsonObject("message")
            Log.d("repository", message.toString())
            val keys: MutableSet<String>? = message.keySet()
            val mutableIterator = keys!!.iterator()
            while (mutableIterator.hasNext()) {
                listOfBreeds.add(mutableIterator.next())
            }
            breedList.postValue(listOfBreeds)
        }
    }

    suspend fun getSingleImage(breed: String) {
        val result = apiService.getSingleImage(breed)
        if (result.body() != null) {
            selectedBreedSingleImage.postValue(result.body())
        }
    }

    suspend fun getAllImages(breed: String) {
        val result = apiService.getAllImages(breed)
        if (result.body() != null) {
            selectedBreedAllImages.postValue(result.body())
        }
    }
}