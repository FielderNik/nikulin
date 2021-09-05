package com.example.nikulin.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nikulin.domain.entities.MemesEntity
import com.example.nikulin.domain.RemoteRepository
import com.example.nikulin.domain.entities.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BestMemesViewModel : ViewModel() {

    private val remoteRepository = RemoteRepository()

    private val bestMemesPrivate = MutableLiveData<List<MemesEntity>>()
    val bestMemes: LiveData<List<MemesEntity>> = bestMemesPrivate
    var countPagesBestMemes = 0
    private var bestMemesList: MutableList<MemesEntity> = mutableListOf()
    val currentPositionBestMemes = MutableLiveData(0)
    val failureBestMemesLiveData = MutableLiveData<Failure>()

    fun getMemes(memesType: String) {
        CoroutineScope(Dispatchers.IO).launch {
            remoteRepository.getMemes(memesType, countPagesBestMemes).fold(
                { failure ->
                    failureBestMemesLiveData.postValue(failure)
                },
                { memesList ->
                    bestMemesList.addAll(memesList)
                    bestMemesPrivate.postValue(bestMemesList)
                    countPagesBestMemes++
                }
            )
        }
    }

}