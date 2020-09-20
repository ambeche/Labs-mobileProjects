package com.example.blescanner

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var bleAdapter: BluetoothAdapter
    private var bleScanning = false
    private lateinit var bLeScanner: BluetoothLeScanner
    private lateinit var bleHandler: Handler
    lateinit var listAdapter: ScannedBleListAdapter

    companion object {
        const val SCAN_PERIOD: Long = 3000
        const val REQUEST_CODE_ENABLE = 0
        const val REQUEST_CODE_FINE_LOCATION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bltManager =  getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bleAdapter = bltManager.adapter
        checkPermissionAndBleStatus()
        btnScan.setOnClickListener{ startBleScan() }

        listAdapter = ScannedBleListAdapter(this)
        lvBleDevices.adapter = listAdapter

    }

    private fun checkPermissionAndBleStatus(): Boolean {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            Log.d("DBG", "No fine location access")
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_FINE_LOCATION
            )
            return true
        } else if (!bleAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_CODE_ENABLE)
        }
        return true
    }

    private fun startBleScan() {
        Log.d("DBG", "Scan start")
        bLeScanner = bleAdapter.bluetoothLeScanner
        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            .build()
        val filter: List<ScanFilter>? = null
        val bleScanCallback = BleScanCallback()
        if (!bleScanning) {
            bleHandler = Handler(Looper.getMainLooper())
            bleHandler.postDelayed({
                bleScanning = false
                bLeScanner.stopScan(bleScanCallback) }, SCAN_PERIOD)
            bleScanning = true
            bLeScanner.startScan(filter, settings, bleScanCallback)
        }else {
            bleScanning = false
            bLeScanner.stopScan(bleScanCallback)
        }


    }

    inner class BleScanCallback  : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            setBleResults(result)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            if (results != null) {
                for (result in results) { setBleResults(result)}
            }
        }
        override fun onScanFailed(errorCode: Int) {
            Log.d ("Scan", errorCode.toString())
        }

        private fun setBleResults(result: ScanResult) {
            val deviceName = result.device

           listAdapter.setAdapter(result)
           Log.d("result", deviceName.address)
        }
    }
}