package com.devlife.nikulin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devlife.nikulin.domain.entities.Failure
import com.devlife.nikulin.domain.entities.MemesEntity
import com.devlife.nikulin.domain.usecase.GetMemesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HotMemesViewModel @Inject constructor(private val getMemesUseCase: GetMemesUseCase) : ViewModel() {

    private val hotMemesPrivate = MutableLiveData<List<MemesEntity>>()
    val hotMemes: LiveData<List<MemesEntity>> = hotMemesPrivate
    var countPages = 0
    private var hotMemesList: MutableList<MemesEntity> = mutableListOf()
    val currentPositionLive = MutableLiveData(0)
    val failureHotMemesLiveData = MutableLiveData<Failure>()

    fun getMemes(memesType: String) {
        CoroutineScope(Dispatchers.IO).launch {
            getMemesUseCase.run(memesType, countPages).fold(
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