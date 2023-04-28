package com.sharma.ankitapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.sharma.ankitapp.R
import com.sharma.ankitapp.core.BaseViewModel
import com.sharma.ankitapp.model.response.Data
import com.sharma.ankitapp.network.Repository
import com.sharma.ankitapp.utils.xtnValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : BaseViewModel() {

    val name= MutableLiveData("")
    val email= MutableLiveData("")
    val pwd= MutableLiveData("")
    private val _register = MutableLiveData<Data<FirebaseUser>?>(null)
    val register: MutableLiveData<Data<FirebaseUser>?> = _register


    fun register()=viewModelScope.launch(Dispatchers.IO) {
       if(validate()){
           _loading.postValue(true)
           val result= name.value?.let { name-> email.value?.let { email ->
               pwd.value?.let { pwd ->
                   Repository.Auth.signup(name,
                       email,pwd
                   )
               }
           } }
           if(result!=null) {
               _loading.postValue(false)
               _register.postValue(result)

           }
       }
    }

    private fun validate():Boolean{
        name.value?.let {
            if(it.isEmpty()){
                err.postValue(R.string.empty_name_warning)
                return false
            }
        }
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
        pwd.value?.let {
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

}