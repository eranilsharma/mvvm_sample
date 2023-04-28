package com.sharma.ankitapp.ui.splash

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

class SplashViewModel : BaseViewModel() {

    private val _isLogin = MutableLiveData<Boolean>(false)
    val isLogin: MutableLiveData<Boolean> = _isLogin

    init {
        checkLogin()
    }

    private fun checkLogin()=viewModelScope.launch(Dispatchers.IO) {
        if (Repository.firebaseAuth?.currentUser != null) {
            _isLogin.postValue(true)
        } else {
            _isLogin.postValue(false)
        }
    }

}