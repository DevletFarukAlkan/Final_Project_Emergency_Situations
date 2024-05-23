package com.devletfarukalkan.finalprojectemergencysituations.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devletfarukalkan.finalprojectemergencysituations.MyApplication
import com.devletfarukalkan.finalprojectemergencysituations.R
import com.devletfarukalkan.finalprojectemergencysituations.adapters.CityAdapter
import com.devletfarukalkan.finalprojectemergencysituations.adapters.ProvinceAdapter
import com.devletfarukalkan.finalprojectemergencysituations.adapters.NeighborhoodAdapter
import com.devletfarukalkan.finalprojectemergencysituations.entities.City
import com.devletfarukalkan.finalprojectemergencysituations.entities.Neighborhood
import com.devletfarukalkan.finalprojectemergencysituations.entities.Placement
import com.devletfarukalkan.finalprojectemergencysituations.entities.Province
import com.devletfarukalkan.finalprojectemergencysituations.viewmodel.PlacementViewModel
import com.devletfarukalkan.finalprojectemergencysituations.viewmodel.PlacementViewModelFactory

class PlacementDetailActivity : AppCompatActivity() {

    private val viewModel: PlacementViewModel by viewModels {
        PlacementViewModelFactory(
            (application as MyApplication).placementRepository,
            (application as MyApplication).cityRepository,
            (application as MyApplication).neighborhoodRepository,
            (application as MyApplication).provinceRepository,
            (application as MyApplication).warehouseRepository
        )
    }

    private lateinit var nameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var explanationEditText: EditText
    private lateinit var citySpinner: Spinner
    private lateinit var provinceSpinner: Spinner
    private lateinit var neighborhoodSpinner: Spinner
    private lateinit var locationButton: Button
    private lateinit var saveButton: Button
    //private lateinit var goBackButton: Button
    //private lateinit var warehouseEditText: EditText

    private var placementId: Int? = null
    private var viewMode: Boolean = false
    private var latitude: Double? = null
    private var longitude: Double? = null

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // Reload the placements if the result is OK
            viewModel.loadPlacements()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placement_detail)

        nameEditText = findViewById(R.id.nameEditText)
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        explanationEditText = findViewById(R.id.explanationEditText)
        citySpinner = findViewById(R.id.citySpinner)
        provinceSpinner = findViewById(R.id.provinceSpinner)
        neighborhoodSpinner = findViewById(R.id.neighborhoodSpinner)
        locationButton = findViewById(R.id.locationButton)
        saveButton = findViewById(R.id.saveButton)
        //goBackButton = findViewById(R.id.goBackButton)
        //warehouseEditText = findViewById(R.id.warehouseEditText)

        val cityAdapter = CityAdapter(this, mutableListOf())
        citySpinner.adapter = cityAdapter

        val provinceAdapter = ProvinceAdapter(this, mutableListOf())
        provinceSpinner.adapter = provinceAdapter

        val neighborhoodAdapter = NeighborhoodAdapter(this, mutableListOf())
        neighborhoodSpinner.adapter = neighborhoodAdapter

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
            neighborhoodAdapter.clear()
            neighborhoodAdapter.addAll(neighborhoods)
            neighborhoodAdapter.notifyDataSetChanged()
        })

        viewModel.loadCities()

        placementId = intent.getIntExtra("placementId", -1).takeIf { it != -1 }
        viewMode = intent.getBooleanExtra("viewMode", false)

        if (placementId != null) {
            viewModel.getPlacementById(placementId!!).observe(this, Observer { placement ->
                nameEditText.setText(placement.name)
                phoneNumberEditText.setText(placement.phoneNumber)
                explanationEditText.setText(placement.explanation)
                viewModel.getNeighborhoodById(placement.neighborhoodId).observe(this, Observer { neighborhood ->
                    neighborhoodSpinner.setSelection(neighborhoodAdapter.getPosition(neighborhood))
                    viewModel.getProvinceById(neighborhood.provinceId).observe(this, Observer { province ->
                        provinceSpinner.setSelection(provinceAdapter.getPosition(province))
                        viewModel.getCityById(province.cityId).observe(this, Observer { city ->
                            citySpinner.setSelection(cityAdapter.getPosition(city))
                        })
                    })
                })
                latitude = placement.latitude
                longitude = placement.longitude
                setupViewMode()
                //warehouseEditText.setText(viewModel.getWarehouseNameByNeighborhoodId(placement.id))
            })
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

        locationButton.setOnClickListener {
            latitude?.let { lat ->
                longitude?.let { lon ->
                    val uri = "geo:$lat,$lon?q=$lat,$lon"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    startActivity(intent)
                }
            }
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val explanation = explanationEditText.text.toString()
            val selectedNeighborhood = neighborhoodSpinner.selectedItem as Neighborhood
            val latitude = this.latitude // Fetch from GPS or user input
            val longitude = this.longitude // Fetch from GPS or user input

            if (placementId != null) {
                // Update placement
                //viewModel.updatePlacement(placementId!!, name, phoneNumber, explanation, selectedNeighborhood.id, latitude, longitude)
            } else {
                // Add new placement
                viewModel.addPlacement(name, phoneNumber, explanation, selectedNeighborhood.id, latitude, longitude)
            }
            setResult(RESULT_OK)
            finish()
        }

        //goBackButton.setOnClickListener {
            //finish()
        //}

        // Disable warehouse input
        //warehouseEditText.isEnabled = false
    }

    private fun setupViewMode() {
        nameEditText.isEnabled = false
        phoneNumberEditText.isEnabled = false
        explanationEditText.isEnabled = false
        citySpinner.isEnabled = false
        provinceSpinner.isEnabled = false
        neighborhoodSpinner.isEnabled = false
        saveButton.visibility = View.GONE
        //goBackButton.visibility = View.VISIBLE
    }

}
