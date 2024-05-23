package com.devletfarukalkan.finalprojectemergencysituations.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devletfarukalkan.finalprojectemergencysituations.MyApplication
import com.devletfarukalkan.finalprojectemergencysituations.R
import com.devletfarukalkan.finalprojectemergencysituations.adapters.PlacementAdapter
import com.devletfarukalkan.finalprojectemergencysituations.adapters.WarehouseAdapter
import com.devletfarukalkan.finalprojectemergencysituations.entities.Placement
import com.devletfarukalkan.finalprojectemergencysituations.viewmodel.PlacementViewModel
import com.devletfarukalkan.finalprojectemergencysituations.viewmodel.PlacementViewModelFactory

class PlacementListActivity : AppCompatActivity() {

    private val viewModel: PlacementViewModel by viewModels {
        PlacementViewModelFactory(
            (application as MyApplication).placementRepository,
            (application as MyApplication).cityRepository,
            (application as MyApplication).neighborhoodRepository,
            (application as MyApplication).provinceRepository,
            (application as MyApplication).warehouseRepository
        )
    }

    private lateinit var placementListView: ListView
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placement_list)

        placementListView = findViewById(R.id.placementListView)
        addButton = findViewById(R.id.addButton)

        val placementAdapter = PlacementAdapter(this, mutableListOf())
        placementListView.adapter = placementAdapter

        viewModel.placements.observe(this, Observer { placements ->
            placementAdapter.clear()
            placementAdapter.addAll(placements)
            placementAdapter.notifyDataSetChanged()
        })

        viewModel.loadPlacements()

        placementListView.setOnItemClickListener { _, _, position, _ ->
            val selectedPlacement = placementAdapter.getItem(position)
            val intent = Intent(this, PlacementDetailActivity::class.java).apply {
                putExtra("placementId", selectedPlacement?.id)
                putExtra("viewMode", true)
            }
            startActivity(intent)
        }

        addButton.setOnClickListener {
            val intent = Intent(this, PlacementDetailActivity::class.java).apply {
                putExtra("viewMode", false)
            }
            startActivity(intent)
        }
    }
}
