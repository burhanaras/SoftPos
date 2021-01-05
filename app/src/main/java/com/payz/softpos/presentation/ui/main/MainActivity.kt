package com.payz.softpos.presentation.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.payz.externalconnection.ui.devicelist.DeviceListActivity
import com.payz.softpos.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initUI()

        observe()

        setStatus("Not connected to POS yet")
    }

    private fun initUI() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_sale, R.id.navigation_history, R.id.navigation_refund,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun observe() {
        viewModel.status.observe(this, Observer { setStatus(it) })

        viewModel.enableBluetooth.observe(this, Observer { })
    }

    private fun setStatus(status: String) {
        Log.d(TAG, "setStatus() called with: status = $status")
        runOnUiThread { supportActionBar?.subtitle = status }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_secure_connect_scan_bluetooth -> {
                discoverPosTerminals()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun discoverPosTerminals() {
        startActivityForResult(DeviceListActivity.newIntent(this), 300)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            300 -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        viewModel.connectBluetooth(data, true)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}