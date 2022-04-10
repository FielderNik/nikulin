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

class BestMemesViewModel @Inject constructor(private val getMemesUseCase: GetMemesUseCase) : ViewModel() {

    private val bestMemesPrivate = MutableLiveData<List<MemesEntity>>()
    val bestMemes: LiveData<List<MemesEntity>> = bestMemesPrivate
    var countPagesBestMemes = 0
    private var bestMemesList: MutableList<MemesEntity> = mutableListOf()
    val currentPositionBestMemes = MutableLiveData(0)
    val failureBestMemesLiveData = MutableLiveData<Failure>()

    fun getMemes(memesType: String) {
        CoroutineScope(Dispatchers.IO).launch {
            getMemesUseCase.run(memesType, countPagesBestMemes).fold(
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