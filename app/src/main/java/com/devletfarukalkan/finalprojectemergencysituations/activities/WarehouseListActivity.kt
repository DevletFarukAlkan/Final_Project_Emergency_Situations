package com.devletfarukalkan.finalprojectemergencysituations.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devletfarukalkan.finalprojectemergencysituations.MyApplication
import com.devletfarukalkan.finalprojectemergencysituations.R
import com.devletfarukalkan.finalprojectemergencysituations.adapters.WarehouseAdapter
import com.devletfarukalkan.finalprojectemergencysituations.entities.Warehouse
import com.devletfarukalkan.finalprojectemergencysituations.viewmodel.WarehouseViewModel
import com.devletfarukalkan.finalprojectemergencysituations.viewmodel.WarehouseViewModelFactory

class WarehouseListActivity : AppCompatActivity() {

    private val viewModel: WarehouseViewModel by viewModels {
        WarehouseViewModelFactory(
            (application as MyApplication).warehouseRepository,
            (application as MyApplication).cityRepository,
            (application as MyApplication).neighborhoodRepository,
            (application as MyApplication).provinceRepository,
        )
    }

    private lateinit var warehouseListView: ListView
    private lateinit var addButton: Button

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // Reload the warehouses if the result is OK
            viewModel.loadWarehouses()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse_list)

        warehouseListView = findViewById(R.id.warehouseListView)
        addButton = findViewById(R.id.addButton)

        val warehouseAdapter = WarehouseAdapter(this, mutableListOf())
        warehouseListView.adapter = warehouseAdapter

        viewModel.warehouses.observe(this, Observer { warehouses ->
            warehouseAdapter.clear()
            if (warehouses != null) {
                warehouseAdapter.addAll(warehouses)
            }
        })

        viewModel.loadWarehouses()

        warehouseListView.setOnItemClickListener { parent, view, position, id ->
            val selectedWarehouse = warehouseAdapter.getItem(position)
            selectedWarehouse?.let { warehouse ->
                viewModel.getWarehouseDetails(warehouse.id).observe(this, Observer { details ->
                    val intent = Intent(this, WarehouseDetailActivity::class.java).apply {
                        putExtra("warehouseId", warehouse.id)
                        putExtra("warehouseName", warehouse.name)
                        putExtra("cityId", details.cityId)
                        putExtra("provinceId", details.provinceId)
                        putExtra("neighborhoodIds", details.neighborhoodIds.toIntArray())
                    }
                    startForResult.launch(intent)
                })
            }
        }

        addButton.setOnClickListener {
            val intent = Intent(this, WarehouseDetailActivity::class.java)
            startForResult.launch(intent)
        }
    }
}
