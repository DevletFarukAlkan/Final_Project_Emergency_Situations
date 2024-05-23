package com.devletfarukalkan.finalprojectemergencysituations.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.devletfarukalkan.finalprojectemergencysituations.entities.Warehouse

class WarehouseAdapter(context: Context, warehouses: List<Warehouse>) : ArrayAdapter<Warehouse>(context, 0, warehouses) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val warehouse = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = warehouse?.name
        return view
    }
}
