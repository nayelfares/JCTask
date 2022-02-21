package com.social.jctask.activities

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.social.jctask.fragments.DetailFragment
import com.social.jctask.utils.Communicator
import com.social.jctask.utils.NetworkMonitorUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import android.view.Gravity

import android.widget.FrameLayout
import com.social.jctask.R


class BaseActivity : AppCompatActivity(), Communicator {
    private val networkMonitor = NetworkMonitorUtil(this)
    private lateinit var navController: NavController
    private var bottomNavigationView: BottomNavigationView? = null
    lateinit var context : Context
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        setUpViews()

        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                if (!isAvailable) {
                    val snack: Snackbar = Snackbar.make(window.decorView.rootView, getString(R.string.No_Connection), Snackbar.LENGTH_LONG)
                    val view = snack.view
                    val params = view.layoutParams as FrameLayout.LayoutParams
                    params.gravity = Gravity.TOP
                    params.topMargin = 100
                    view.layoutParams = params
                    snack.show()
                    }
            }
        }

    }
    private fun setUpViews(){
        navController = findNavController(R.id.nav_host_fragment)

        bottomNavigationView = findViewById(R.id.bottomNavView)

        bottomNavigationView?.isItemHorizontalTranslationEnabled = false
        bottomNavigationView?.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()

    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    override fun onBackPressed() {
        if (navController.graph.startDestination == navController.currentDestination?.id)
        {
            if (backPressedOnce)
            {
                super.onBackPressed()
                return
            }

            backPressedOnce = true
            Toast.makeText(this, getText(R.string.press_back), Toast.LENGTH_SHORT).show()

            Handler().postDelayed(2000) {
                backPressedOnce = false
            }
        }
        else {
            super.onBackPressed()
        }
    }

    override fun passDataCom(editTextInput: String) {
        val transaction = this.supportFragmentManager.beginTransaction()
        val detailFragment  = DetailFragment()
        detailFragment.arguments = Bundle()
        transaction.replace(R.id.nav_host_fragment, detailFragment)
        transaction.commit()
    }
}