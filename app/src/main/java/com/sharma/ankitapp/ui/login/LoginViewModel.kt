package com.sharma.ankitapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.sharma.ankitapp.R
import com.sharma.ankitapp.core.BaseViewModel
import com.sharma.ankitapp.model.response.Data
import com.sharma.ankitapp.network.Repository
import com.sharma.ankitapp.utils.xtnValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {
    val email=MutableLiveData("")
    val password=MutableLiveData("")
    private val _loginFlow = MutableLiveData<Data<FirebaseUser>?>(null)
    val loginFlow: MutableLiveData<Data<FirebaseUser>?> = _loginFlow

    fun login()=viewModelScope.launch(Dispatchers.IO) {
        if(validate()){
            _loading.postValue(true)
           val result= email.value?.let { password.value?.let { it1 -> Repository.Auth.login(it, it1) } }
            if(result!=null) {
                _loading.postValue(false)
                _loginFlow.postValue(result)
            }

        }
    }

    fun validate():Boolean{
        email.value?.let {
            if(it.isEmpty()){
                err.postValue(R.string.enter_email)
                return false
            }
            else if(it.xtnValidEmail().not()){
                err.postValue(R.string.enter_valid_email)
                return false
            }
        }
        password.value?.let {
            if(it.isEmpty()){
                err.postValue(R.string.enter_password)
                return false
            }
            else if(it.length<5){
                err.postValue(R.string.enter_valid_password)
                return false
            }
        }
        return true
    }
    fun register(){


    }

}