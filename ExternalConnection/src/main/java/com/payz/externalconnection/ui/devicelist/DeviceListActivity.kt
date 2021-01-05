package com.payz.externalconnection.ui.devicelist

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.core.view.isGone
import com.payz.externalconnection.R

class DeviceListActivity : AppCompatActivity() {

    private var mBtAdapter: BluetoothAdapter? = null
    private var mNewDevicesArrayAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setup the window
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
        setContentView(R.layout.activity_device_list)

        // Set result CANCELED in case the user backs out
        setResult(Activity.RESULT_CANCELED)

        // Initialize the button to perform device discovery
        val scanButton = findViewById<Button>(R.id.button_scan)
        scanButton.setOnClickListener { v ->
            doDiscovery()
            v.visibility = View.GONE
        }

        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
        val pairedDevicesArrayAdapter = ArrayAdapter<String>(this, R.layout.device_name)
        mNewDevicesArrayAdapter = ArrayAdapter(this, R.layout.device_name)

        // Find and set up the ListView for paired devices
        val pairedListView = findViewById<ListView>(R.id.paired_devices)
        pairedListView.adapter = pairedDevicesArrayAdapter
        pairedListView.onItemClickListener = mDeviceClickListener

        // Find and set up the ListView for newly discovered devices
        val newDevicesListView = findViewById<ListView>(R.id.new_devices)
        newDevicesListView.adapter = mNewDevicesArrayAdapter
        newDevicesListView.onItemClickListener = mDeviceClickListener

        // Register for broadcasts when a device is discovered
        var filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        this.registerReceiver(mReceiver, filter)

        // Register for broadcasts when discovery has finished
        filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        this.registerReceiver(mReceiver, filter)

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter()

        // Get a set of currently paired devices
        val pairedDevices = mBtAdapter?.bondedDevices

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices?.size!! > 0) {
            findViewById<View>(R.id.title_paired_devices).visibility = View.VISIBLE
            for (device in pairedDevices) {
                pairedDevicesArrayAdapter.add("${device.name} ${device.address}".trimIndent())
            }
        } else {
            findViewById<TextView>(R.id.error_message).isGone = false
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter!!.cancelDiscovery()
        }

        // Unregister broadcast listeners
        unregisterReceiver(mReceiver)
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private fun doDiscovery() {
        Log.d(TAG, "doDiscovery()")

        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true)
        setTitle(R.string.scanning)

        // Turn on sub-title for new devices
        findViewById<View>(R.id.title_new_devices).visibility = View.VISIBLE

        // If we're already discovering, stop it
        if (mBtAdapter!!.isDiscovering) {
            mBtAdapter!!.cancelDiscovery()
        }

        // Request discover from BluetoothAdapter
        mBtAdapter!!.startDiscovery()
    }

    /**
     * The on-click listener for all devices in the ListViews
     */
    private val mDeviceClickListener =
        AdapterView.OnItemClickListener { av, v, arg2, arg3 -> // Cancel discovery because it's costly and we're about to connect
            mBtAdapter!!.cancelDiscovery()

            // Get the device MAC address, which is the last 17 chars in the View
            val info = (v as TextView).text.toString()
            val address = info.substring(info.length - 17)

            // Create the result Intent and include the MAC address
            val intent = Intent()
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address)

            // Set result and finish this Activity
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    /**
     * The BroadcastReceiver that listens for discovered devices and changes the title when
     * discovery is finished
     */
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND == action) {
                // Get the BluetoothDevice object from the Intent
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                // If it's already paired, skip it, because it's been listed already
                if (device != null && device.bondState != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter!!.add("${device.name} ${device.address}".trimIndent())
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                setProgressBarIndeterminateVisibility(false)
                setTitle(R.string.select_device)
                if (mNewDevicesArrayAdapter!!.count == 0) {
                    val noDevices = resources.getText(R.string.none_found).toString()
                    mNewDevicesArrayAdapter!!.add(noDevices)
                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, DeviceListActivity::class.java)
        }

        private const val TAG = "DeviceListActivity"
        var EXTRA_DEVICE_ADDRESS = "device_address"
    }
}