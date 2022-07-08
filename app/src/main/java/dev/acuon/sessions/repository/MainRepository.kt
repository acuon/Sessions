package dev.acuon.sessions.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import dev.acuon.sessions.data.api.ApiService
import dev.acuon.sessions.ui.model.Dog
import dev.acuon.sessions.ui.model.image.BreedImageAll
import dev.acuon.sessions.ui.model.image.BreedImageSingle
import dev.acuon.sessions.utils.Extensions.randomIndex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception


class MainRepository(
    private val apiService: ApiService
) {
    private val breedList = MutableLiveData<List<String>>()
    private val selectedBreedSingleImage = MutableLiveData<BreedImageSingle>()
    private val selectedBreedAllImages = MutableLiveData<BreedImageAll>()
    private val listOfBreeds by lazy { ArrayList<String>() }
    private val fiveBreeds by lazy { ArrayList<Dog>() }
    private val listOfFiveRandomBreeds = MutableLiveData<List<Dog>>()

    val allBreeds: LiveData<List<String>>
        get() = breedList

    val breedImage: LiveData<BreedImageSingle>
        get() = selectedBreedSingleImage

    val breedImages: LiveData<BreedImageAll>
        get() = selectedBreedAllImages

    val fiveRandomBreeds: LiveData<List<Dog>>
        get() = listOfFiveRandomBreeds

    suspend fun getFiveRandomBreed() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = async {
                    apiService.getAllBreeds()
                }.await()
                if (response.isSuccessful) {
                    Log.d("isSuccessful", response.body()!!.getAsJsonObject("message").toString())
                    val message: JsonObject = response.body()!!.getAsJsonObject("message")
                    Log.d("repository", message.toString())
                    val keys: MutableSet<String>? = message.keySet()
                    val mutableIterator = keys!!.iterator()
                    while (mutableIterator.hasNext()) {
                        listOfBreeds.add(mutableIterator.next())
                    }
                    breedList.postValue(listOfBreeds)
                    listOfBreeds.shuffle()
                    val i1 = listOfBreeds.randomIndex()
                    val i2 = listOfBreeds.randomIndex()
                    val i3 = listOfBreeds.randomIndex()
                    val i4 = listOfBreeds.randomIndex()
                    val i5 = listOfBreeds.randomIndex()
                    val breed1 = listOfBreeds[i1]
                    val breed2 = listOfBreeds[i2]
                    val breed3 = listOfBreeds[i3]
                    val breed4 = listOfBreeds[i4]
                    val breed5 = listOfBreeds[i5]
                    val response1 = async {
                        apiService.getSingleImage(breed1)
                    }.await()
                    val response2 = async {
                        apiService.getSingleImage(breed2)
                    }.await()
                    val response3 = async {
                        apiService.getSingleImage(breed3)
                    }.await()
                    val response4 = async {
                        apiService.getSingleImage(breed4)
                    }.await()
                    val response5 = async {
                        apiService.getSingleImage(breed5)
                    }.await()
                    if (response1.isSuccessful || response2.isSuccessful || response3.isSuccessful || response4.isSuccessful || response5.isSuccessful) {
                        Log.d("chain1", response1.body()!!.message)
                        Log.d("chain2", response2.body()!!.message)
                        Log.d("chain3", response3.body()!!.message)
                        Log.d("chain4", response4.body()!!.message)
                        Log.d("chain5", response5.body()!!.message)
                        Log.d("the indices were", "$i1, $i2, $i3, $i4, $i5")
                        fiveBreeds.clear()
                        fiveBreeds.add(Dog(breed1, response1.body()!!.message))
                        fiveBreeds.add(Dog(breed2, response2.body()!!.message))
                        fiveBreeds.add(Dog(breed3, response3.body()!!.message))
                        fiveBreeds.add(Dog(breed4, response4.body()!!.message))
                        fiveBreeds.add(Dog(breed5, response5.body()!!.message))
                        listOfFiveRandomBreeds.postValue(fiveBreeds)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Exception", e.toString())
        }
    }

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