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

class HotMemesViewModel : ViewModel() {

    private val remoteRepository = RemoteRepository()

    private val hotMemesPrivate = MutableLiveData<List<MemesEntity>>()
    val hotMemes: LiveData<List<MemesEntity>> = hotMemesPrivate
    var countPages = 0
    private var hotMemesList: MutableList<MemesEntity> = mutableListOf()
    val currentPositionLive = MutableLiveData(0)
    val failureHotMemesLiveData = MutableLiveData<Failure>()

    fun getMemes(memesType: String) {
        CoroutineScope(Dispatchers.IO).launch {
            remoteRepository.getMemes(memesType, countPages).fold(
                { failure ->
                    failureHotMemesLiveData.postValue(failure)
                },
                { memesList ->
                    hotMemesList.addAll(memesList)
                    hotMemesPrivate.postValue(hotMemesList)
                    countPages++
                }
            )
        }
    }
}