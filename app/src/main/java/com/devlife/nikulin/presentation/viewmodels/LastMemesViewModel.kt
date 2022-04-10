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

class LastMemesViewModel @Inject constructor(private val getMemesUseCase: GetMemesUseCase) : ViewModel() {


    private val latestMemesPrivate = MutableLiveData<List<MemesEntity>>()
    val latestMemes: LiveData<List<MemesEntity>> = latestMemesPrivate
    var countPagesLatestMemes = 0
    private var latestMemesList: MutableList<MemesEntity> = mutableListOf()
    val currentPositionLatestMemes = MutableLiveData(0)
    val failureLiveData = MutableLiveData<Failure>()


    fun getMemes(memesType: String) {
        CoroutineScope(Dispatchers.IO).launch {
            getMemesUseCase.run(memesType, countPagesLatestMemes).fold(
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