package com.sharma.ankitapp.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.sharma.ankitapp.core.BaseViewModel
import com.sharma.ankitapp.model.response.Data
import com.sharma.ankitapp.network.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel : BaseViewModel() {
    private val _userDetails = MutableLiveData<Data<FirebaseUser>?>(null)
    val userDetails: MutableLiveData<Data<FirebaseUser>?> = _userDetails
    val email=MutableLiveData("")
    val name=MutableLiveData("")
    val logout=MutableLiveData(false)

    init {
        getUserDetails()
    }

    private fun getUserDetails()=viewModelScope.launch(Dispatchers.IO){
        _loading.postValue(true)
        val result=Repository.Auth.getUserDetails()
        if(result!=null){
            _loading.postValue(false)
            _userDetails.postValue(result)
        }
    }

    fun logout()=viewModelScope.launch(Dispatchers.IO){
        val result=Repository.Auth.logout()
        if(result!=null){
            logout.postValue(true)
        }
        else
            logout.postValue(false)
    }
}