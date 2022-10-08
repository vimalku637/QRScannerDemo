package com.ctl.qrscannerdemo

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton


private const val CAMERA_REQUEST_CODE=101

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        setUpPermissions()
    }

    private fun initView() {
        val scannerView = findViewById<MaterialButton>(R.id.btnScannerView)
        scannerView.setOnClickListener {
            loadFragmentOnActivity(1)
        }
        val barcodeScanner = findViewById<MaterialButton>(R.id.btnBarcode)
        barcodeScanner.setOnClickListener {
        }
    }

    private fun loadFragmentOnActivity(i: Int) {
        if (i==1) {
            title = "Code Scanner"
            supportFragmentManager.beginTransaction()
                .add(R.id.addFragmentOnMainActivity, CodeScannerViewFragment())
                .addToBackStack(null) //when you open fragment then use addToBackStack(null) to close fragment on click.
                .commit()
        }
    }

    private fun setUpPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
        android.Manifest.permission.CAMERA)
        if (permission!= PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
        arrayOf(android.Manifest.permission.CAMERA),
        CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You need the camera permission to be able to use this app.", Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(this, "Permission granted successfully.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}