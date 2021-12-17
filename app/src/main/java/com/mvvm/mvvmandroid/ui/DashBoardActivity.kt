package com.mvvm.mvvmandroid.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.mvvm.mvvmandroid.R
import com.mvvm.mvvmandroid.core.BaseActivity
import com.mvvm.mvvmandroid.databinding.ActivityDashboardBinding


class DashBoardActivity : BaseActivity<ActivityDashboardBinding>() {


    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override val layoutId = R.layout.activity_dashboard

    override fun handleViews() {

        binding.apply {
            navController = findNavController(R.id.nav_dashboard)
            NavigationUI.setupWithNavController(myBottomNavigationView, navController)
            navController.addOnDestinationChangedListener { controller, destination, arguments ->

            }
        }
    }

    override fun handleObservers() {

    }

}

