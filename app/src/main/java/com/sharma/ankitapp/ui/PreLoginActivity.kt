package com.sharma.ankitapp.ui

import android.os.Bundle
import com.sharma.ankitapp.R
import com.sharma.ankitapp.core.BaseActivity
import com.sharma.ankitapp.databinding.ActivityPreLoginBinding


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