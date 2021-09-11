package com.example.nikulin.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nikulin.domain.RemoteRepository
import com.example.nikulin.domain.entities.Failure
import com.example.nikulin.domain.entities.MemesEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LastMemesViewModel @Inject constructor(private val remoteRepository: RemoteRepository) : ViewModel() {


    private val latestMemesPrivate = MutableLiveData<List<MemesEntity>>()
    val latestMemes: LiveData<List<MemesEntity>> = latestMemesPrivate
    var countPagesLatestMemes = 0
    private var latestMemesList: MutableList<MemesEntity> = mutableListOf()
    val currentPositionLatestMemes = MutableLiveData(0)
    val failureLiveData = MutableLiveData<Failure>()


    fun getMemes(memesType: String) {
        CoroutineScope(Dispatchers.IO).launch {
            remoteRepository.getMemes(memesType, countPagesLatestMemes).fold(
                { failure ->
                    failureLiveData.postValue(failure)
                },
                { memesList ->
                    latestMemesList.addAll(memesList)
                    latestMemesPrivate.postValue(latestMemesList)
                    countPagesLatestMemes++
                }
            )
        }
    }

}