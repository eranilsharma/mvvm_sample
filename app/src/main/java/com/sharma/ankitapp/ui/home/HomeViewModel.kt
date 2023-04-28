package com.sharma.ankitapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sharma.ankitapp.core.BaseViewModel
import com.sharma.ankitapp.model.response.Data
import com.sharma.ankitapp.network.Repository
import com.sharma.ankitapp.ui.home.model.SubjectList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    private val _subjectList = MutableLiveData<Data<Any>>(null)
    val subjectList: MutableLiveData<Data<Any>> = _subjectList

    init {
        getSubject()
    }

    private fun getSubject() = viewModelScope.launch(Dispatchers.IO) {
        _loading.postValue(true)
        val result=Repository.GetData.getSubjectList()
        if(result!=null){
            _loading.postValue(false)
            _subjectList.postValue(result)
        }

    }


}