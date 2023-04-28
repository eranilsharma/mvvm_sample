package com.sharma.ankitapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.sharma.ankitapp.R
import com.sharma.ankitapp.core.BaseActivity
import com.sharma.ankitapp.databinding.ActivityDashboardBinding


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
                /*if (destination.id == R.id.tab1Fragment || destination.id == R.id.tab2Fragment
                    || destination.id == R.id.tab5Fragment
                ) {
                    myBottomNavigationView.visibility = View.VISIBLE
                } else {
                    myBottomNavigationView.visibility = View.GONE
                }*/
            }
        }
    }

    override fun handleObservers() {

    }

}

