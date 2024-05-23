package com.devletfarukalkan.finalprojectemergencysituations.activities

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devletfarukalkan.finalprojectemergencysituations.MyApplication
import com.devletfarukalkan.finalprojectemergencysituations.R
import com.devletfarukalkan.finalprojectemergencysituations.adapters.CityAdapter
import com.devletfarukalkan.finalprojectemergencysituations.adapters.ProvinceAdapter
import com.devletfarukalkan.finalprojectemergencysituations.entities.City
import com.devletfarukalkan.finalprojectemergencysituations.entities.Neighborhood
import com.devletfarukalkan.finalprojectemergencysituations.entities.Province
import com.devletfarukalkan.finalprojectemergencysituations.viewmodel.WarehouseViewModel
import com.devletfarukalkan.finalprojectemergencysituations.viewmodel.WarehouseViewModelFactory

class WarehouseDetailActivity : AppCompatActivity() {

    private val viewModel: WarehouseViewModel by viewModels {
        WarehouseViewModelFactory(
            (application as MyApplication).warehouseRepository,
            (application as MyApplication).cityRepository,
            (application as MyApplication).neighborhoodRepository,
            (application as MyApplication).provinceRepository,
        )
    }

    private lateinit var warehouseNameEditText: EditText
    private lateinit var citySpinner: Spinner
    private lateinit var provinceSpinner: Spinner
    private lateinit var neighborhoodButton: Button
    private lateinit var saveButton: Button

    private var warehouseId: Int? = null
    private var selectedNeighborhoods = mutableListOf<Neighborhood>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse_detail)

        warehouseNameEditText = findViewById(R.id.warehouseNameEditText)
        citySpinner = findViewById(R.id.citySpinner)
        provinceSpinner = findViewById(R.id.provinceSpinner)
        neighborhoodButton = findViewById(R.id.neighborhoodButton)
        saveButton = findViewById(R.id.saveButton)

        val cityAdapter = CityAdapter(this, mutableListOf())
        citySpinner.adapter = cityAdapter

        val provinceAdapter = ProvinceAdapter(this, mutableListOf())
        provinceSpinner.adapter = provinceAdapter

        viewModel.cities.observe(this, Observer { cities ->
            cityAdapter.clear()
            cityAdapter.addAll(cities)
            cityAdapter.notifyDataSetChanged()
        })

        viewModel.provinces.observe(this, Observer { provinces ->
            provinceAdapter.clear()
            provinceAdapter.addAll(provinces)
            provinceAdapter.notifyDataSetChanged()
        })

        viewModel.neighborhoods.observe(this, Observer { neighborhoods ->
            Log.d("WarehouseDetailActivity", "Neighborhoods loaded: ${neighborhoods?.size}")
            updateNeighborhoodsDialog(neighborhoods)
        })

        warehouseId = intent.getIntExtra("warehouseId", -1).takeIf { it != -1 }
        var warehouseName = intent.getStringExtra("warehouseName")
        val cityId = intent.getIntExtra("cityId", -1)
        val provinceId = intent.getIntExtra("provinceId", -1)
        var neighborhoodIds = intent.getIntArrayExtra("neighborhoodIds")

        if (warehouseId != null) {
            warehouseNameEditText.setText(warehouseName)
            viewModel.loadCities()
            citySpinner.setSelection(cityAdapter.getPosition(viewModel.getCityById(cityId)))
            viewModel.loadProvincesByCity(cityId)
            provinceSpinner.setSelection(provinceAdapter.getPosition(viewModel.getProvinceById(provinceId)))
            viewModel.loadNeighborhoodsByProvince(provinceId)
            neighborhoodIds?.forEach { id ->
                viewModel.getNeighborhoodById(id)?.let { selectedNeighborhoods.add(it) }
            }
            updateNeighborhoodButtonText()
        } else {
            viewModel.loadCities()
        }

        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCity = cityAdapter.getItem(position)!!
                viewModel.loadProvincesByCity(selectedCity.id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        provinceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedProvince = provinceAdapter.getItem(position)!!
                viewModel.loadNeighborhoodsByProvince(selectedProvince.id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        neighborhoodButton.setOnClickListener {
            viewModel.neighborhoods.value?.let { neighborhoods ->
                showMultiSelectDialog(neighborhoods)
            }
        }

        saveButton.setOnClickListener {
            warehouseName = warehouseNameEditText.text.toString()
            neighborhoodIds = selectedNeighborhoods.map { it.id }.toIntArray()

            if (warehouseId != null) {
                viewModel.updateWarehouse(warehouseId!!, warehouseName!!, neighborhoodIds!!)
            } else {
                viewModel.addWarehouse(warehouseName!!, neighborhoodIds!!)
            }
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun updateNeighborhoodsDialog(neighborhoods: List<Neighborhood>) {
        // Implementation to update the multi-select dialog if needed
        if (neighborhoods.isNotEmpty()) {
            showMultiSelectDialog(neighborhoods)
        }
    }

    private fun showMultiSelectDialog(neighborhoods: List<Neighborhood>) {
        val neighborhoodNames = neighborhoods.map { it.name }.toTypedArray()
        val selectedItems = BooleanArray(neighborhoods.size) { index ->
            selectedNeighborhoods.contains(neighborhoods[index])
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Neighborhoods")
            .setMultiChoiceItems(neighborhoodNames, selectedItems) { _, which, isChecked ->
                if (isChecked) {
                    selectedNeighborhoods.add(neighborhoods[which])
                } else {
                    selectedNeighborhoods.remove(neighborhoods[which])
                }
            }
            .setPositiveButton("OK") { _, _ ->
                updateNeighborhoodButtonText()
            }
            .setNegativeButton("Cancel", null)
            .setNeutralButton("Select All") { _, _ ->
                selectedNeighborhoods.clear()
                selectedNeighborhoods.addAll(neighborhoods)
                updateNeighborhoodButtonText()
            }
            .setNeutralButton("De-select All") { _, _ ->
                selectedNeighborhoods.clear()
                updateNeighborhoodButtonText()
            }
        builder.show()
    }

    private fun updateNeighborhoodButtonText() {
        if (selectedNeighborhoods.isEmpty()) {
            neighborhoodButton.text = "Select Neighborhoods"
        } else {
            neighborhoodButton.text = "Neighborhoods Selected"
        }
    }
}
