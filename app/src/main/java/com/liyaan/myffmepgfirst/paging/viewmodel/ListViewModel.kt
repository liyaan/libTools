package com.liyaan.myffmepgfirst.paging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.liyaan.myffmepgfirst.paging.model.ListDataX
import com.liyaan.myffmepgfirst.paging.model.ListEntity
import com.liyaan.myffmepgfirst.paging.pag.ListPagingSource
import kotlinx.coroutines.flow.Flow

class ListViewModel:ViewModel() {
    private val listData by lazy {
        Pager(config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 1,
            initialLoadSize = 20
        ),pagingSourceFactory = {ListPagingSource()}).flow.cachedIn(viewModelScope)
    }
    fun loadList(): Flow<PagingData<ListDataX>>  = listData
}