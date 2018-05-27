package com.lumossmart.lumossmarthome.ui.adapter

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.IconeEnum
import kotlinx.android.synthetic.main.item_icone.view.*

class ItemIconeAdapter (private val icones: Array<IconeEnum>,
                          private val contexto: Context?) : BaseAdapter(){


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(contexto).inflate(R.layout.item_icone, parent, false)

        val icone = icones[position]

        var resources = contexto!!.resources
        val idIcone = resources.getIdentifier(icone.icone, "drawable", contexto!!.packageName)
        var drawable = resources.getDrawable(idIcone, null)
        viewCriada.item_icone.setImageDrawable(drawable)

        return viewCriada
    }

    override fun getItem(position: Int): IconeEnum {
        return icones[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return icones.size
    }
}