package com.devletfarukalkan.finalprojectemergencysituations.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.devletfarukalkan.finalprojectemergencysituations.entities.Placement

class PlacementAdapter(context: Context, placements: List<Placement>) : ArrayAdapter<Placement>(context, 0, placements) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val placement = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = placement?.name
        return view
    }
}