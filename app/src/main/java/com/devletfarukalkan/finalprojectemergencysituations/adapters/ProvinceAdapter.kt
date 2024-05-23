package com.devletfarukalkan.finalprojectemergencysituations.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.devletfarukalkan.finalprojectemergencysituations.entities.Province

class ProvinceAdapter(context: Context, provinces: List<Province>) : ArrayAdapter<Province>(context, android.R.layout.simple_spinner_item, provinces) {
    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.text = getItem(position)?.name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.text = getItem(position)?.name
        return view
    }
}