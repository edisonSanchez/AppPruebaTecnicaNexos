package com.edisonsanchez.apppruebatecnicanexos.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.edisonsanchez.apppruebatecnicanexos.R
import com.edisonsanchez.apppruebatecnicanexos.data.hideKeyBoard
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener{

    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var containerFragment: FrameLayout
    private val authorizationTransactionFragment = AuthorizationTransactionFragment()
    private val approvedTransactionsFragment = ApprovedTransactionsFragment()
    private val searchTransactionsFragment = SearchTransactionsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        containerFragment = findViewById(R.id.fragmentContainer)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.title = getString(R.string.client_name)
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {
                currentFocus?.let { hideKeyBoard(this@MainActivity, it) }
            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerStateChanged(newState: Int) {

            }
        })
        navigationView.setNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,
            authorizationTransactionFragment).commitNow()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,
            approvedTransactionsFragment).commitNow()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,
            searchTransactionsFragment).commitNow()
        supportFragmentManager.beginTransaction().show(authorizationTransactionFragment).commitNow()
        supportFragmentManager.beginTransaction().hide(approvedTransactionsFragment).commitNow()
        supportFragmentManager.beginTransaction().hide(searchTransactionsFragment).commitNow()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.authorizationTransaction -> {
                supportFragmentManager.beginTransaction().hide(approvedTransactionsFragment)
                    .commitNow()
                supportFragmentManager.beginTransaction().hide(searchTransactionsFragment)
                    .commitNow()
                supportFragmentManager.beginTransaction().show(authorizationTransactionFragment)
                    .commitNow()
            }
            R.id.approvedTransactions -> {
                supportFragmentManager.beginTransaction().hide(searchTransactionsFragment)
                    .commitNow()
                supportFragmentManager.beginTransaction().hide(authorizationTransactionFragment)
                    .commitNow()
                supportFragmentManager.beginTransaction().show(approvedTransactionsFragment)
                    .commitNow()
            }
            R.id.searchTransactions -> {
                supportFragmentManager.beginTransaction().hide(approvedTransactionsFragment)
                    .commitNow()
                supportFragmentManager.beginTransaction().hide(authorizationTransactionFragment)
                    .commitNow()
                supportFragmentManager.beginTransaction().show(searchTransactionsFragment)
                    .commitNow()
            }
        }
        return true
    }
}