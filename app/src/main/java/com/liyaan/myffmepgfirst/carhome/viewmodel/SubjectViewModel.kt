package com.liyaan.myffmepgfirst.carhome.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.liyaan.myffmepgfirst.carhome.model.SubjectDataDataX
import com.liyaan.myffmepgfirst.carhome.repository.Repository

class SubjectViewModel @ViewModelInject constructor(private val subjectRepository:Repository):ViewModel() {

    val data:LiveData<PagingData<SubjectDataDataX>> =
        subjectRepository.fetchSubjectList().cachedIn(viewModelScope).asLiveData()

}