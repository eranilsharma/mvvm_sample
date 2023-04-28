package com.sharma.ankitapp.core

import android.app.Application
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseApp
import com.sharma.ankitapp.network.ApiService
import com.sharma.ankitapp.utils.DebugTree
import com.sharma.ankitapp.utils.Event
import com.google.gson.Gson
import com.sharma.ankitapp.BuildConfig
import com.squareup.picasso.Picasso
import timber.log.Timber


/*
* THIS CLASS RESPONSIBLE FOR MANY APP LEVEL DEPENDENCIES AND CONTAINS APP LEVEL OBJECTS
**/
class BaseApp : Application() {

    lateinit var picasso: Picasso
    lateinit var gson: Gson

    companion object {
        lateinit var INSTANCE: BaseApp
    }


    override fun onCreate() {
        super.onCreate()
        // ALL INITIALIZING THING GOES HERE
        INSTANCE = this
        FirebaseApp.initializeApp(this)
        initAppDependencies()

    }

    private fun initAppDependencies() {
        picasso = ApiService.picasso
        gson = ApiService.gson
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

}


abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: DB
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this


        handleViews()
        handleObservers()
    }

    protected abstract fun handleViews()
    protected abstract fun handleObservers()

}

open class BaseViewModel : ViewModel() {

    private val _err = Event<@receiver:StringRes Int>()
    val err = _err

    private val _msg = Event<String>()
    val msg = _msg

    val _loading = Event<Boolean>()
    val loading = _loading

    val _throwable = Event<Throwable>()
    val throwable = _throwable


}




