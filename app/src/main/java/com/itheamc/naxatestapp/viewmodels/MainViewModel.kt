package com.itheamc.naxatestapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.itheamc.naxatestapp.models.Entry
import com.itheamc.naxatestapp.pagination.EntryPagingSource
import com.itheamc.naxatestapp.repositories.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.fetchEntries()
        }
    }


    /**
     * Paging data flow
     */
    val entries = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
    ) {
        EntryPagingSource(mainRepository)
    }.flow.cachedIn(viewModelScope)


    /**
     * Error flow
     */
    val error = mainRepository.error


}