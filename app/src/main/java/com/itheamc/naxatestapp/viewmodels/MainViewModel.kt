package com.itheamc.naxatestapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.itheamc.naxatestapp.pagination.EntryPagingSource
import com.itheamc.naxatestapp.repositories.MainRepository

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {


    val entries = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
    ) {
        EntryPagingSource(mainRepository.entryDao)
    }.flow.cachedIn(viewModelScope)

}