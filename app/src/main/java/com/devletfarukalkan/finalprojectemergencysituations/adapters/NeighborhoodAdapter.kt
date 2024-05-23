package com.devletfarukalkan.finalprojectemergencysituations.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.devletfarukalkan.finalprojectemergencysituations.entities.Neighborhood

class NeighborhoodAdapter(context: Context, neighborhoods: List<Neighborhood>) : ArrayAdapter<Neighborhood>(context, 0, neighborhoods) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false)
        val neighborhood = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = neighborhood?.name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        val neighborhood = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = neighborhood?.name
        return view
    }
}