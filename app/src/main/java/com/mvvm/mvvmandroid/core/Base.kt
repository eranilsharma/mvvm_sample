package com.mvvm.mvvmandroid.core

import android.app.Application
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.mvvm.mvvmandroid.BuildConfig
import com.mvvm.mvvmandroid.network.ApiService
import com.mvvm.mvvmandroid.utils.DebugTree
import com.mvvm.mvvmandroid.utils.Event
import com.google.gson.Gson
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

    val _err = Event<@StringRes Int>()
    val err = _err

    val _msg = Event<String>()
    val msg = _msg

}




