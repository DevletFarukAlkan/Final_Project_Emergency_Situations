package com.devletfarukalkan.finalprojectemergencysituations.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.devletfarukalkan.finalprojectemergencysituations.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewPlacementsButton: Button
    private lateinit var viewWarehousesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPlacementsButton = findViewById(R.id.viewPlacementsButton)
        viewWarehousesButton = findViewById(R.id.viewWarehousesButton)

        viewPlacementsButton.setOnClickListener {
            val intent = Intent(this, PlacementListActivity::class.java)
            startActivity(intent)
        }

        viewWarehousesButton.setOnClickListener {
            val intent = Intent(this, WarehouseListActivity::class.java)
            startActivity(intent)
        }
    }
}
