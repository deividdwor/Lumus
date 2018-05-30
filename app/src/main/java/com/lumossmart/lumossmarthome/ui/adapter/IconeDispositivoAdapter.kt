package com.lumossmart.lumossmarthome.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.IconeAmbienteEnum
import com.lumossmart.lumossmarthome.model.IconeDispositivoEnum
import kotlinx.android.synthetic.main.item_icone.view.*

class IconeDispositivoAdapter (private val icones: Array<IconeDispositivoEnum>,
                               private val cor: Drawable,
                               private val contexto: Context?) : BaseAdapter(){


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(contexto).inflate(R.layout.item_icone, parent, false)

        val icone = icones[position]

        viewCriada.item_icone.setImageDrawable(contexto!!.getDrawableByName(icone.icone))

        viewCriada.fundo.setImageDrawable(cor)

        return viewCriada
    }

    override fun getItem(position: Int): IconeDispositivoEnum {
        return icones[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return icones.size
    }
}