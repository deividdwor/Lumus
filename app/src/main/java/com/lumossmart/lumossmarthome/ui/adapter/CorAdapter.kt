package com.lumossmart.lumossmarthome.ui.adapter

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Spinner
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.CorEnum
import kotlinx.android.synthetic.main.item_icone.view.*

class CorAdapter (private val cores: Array<CorEnum>,
                  private val contexto: Context?) : BaseAdapter(){


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(contexto).inflate(R.layout.item_color, parent, false)

        val cor = cores[position]

        viewCriada.fundo.setImageDrawable(contexto!!.getDrawableByName(cor.cor))

        return viewCriada
    }

    override fun getItem(position: Int): CorEnum {
        return cores[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return cores.size
    }
}