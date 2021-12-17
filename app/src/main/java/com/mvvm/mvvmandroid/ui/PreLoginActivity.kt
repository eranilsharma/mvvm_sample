package com.mvvm.mvvmandroid.ui

import android.os.Bundle
import com.mvvm.mvvmandroid.R
import com.mvvm.mvvmandroid.core.BaseActivity
import com.mvvm.mvvmandroid.databinding.ActivityPreLoginBinding


class PreLoginActivity : BaseActivity<ActivityPreLoginBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override val layoutId: Int
        get() = R.layout.activity_pre_login

    override fun handleViews() {

    }

    override fun handleObservers() {

    }
}