package com.lumossmart.lumossmarthome.ui.adapter

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.Ambiente
import kotlinx.android.synthetic.main.item_ambiente.view.*

class ListaAmbientesAdapter(private val ambientes: List<Ambiente>,
                            private val contexto: Context?) : BaseAdapter(){

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(contexto).inflate(R.layout.item_ambiente, parent, false)

        val ambiente = ambientes[position]
        viewCriada.item_ambiente_nome.text = ambiente.nome

        viewCriada.item_ambiente_cor.setImageDrawable(contexto!!.getDrawableByName(ambiente.cor))

        viewCriada.item_ambiente_icone.setImageDrawable(contexto!!.getDrawableByName(ambiente.icone))


        return viewCriada
    }

    override fun getItem(position: Int): Ambiente {
        return ambientes[position]
    }

    override fun getItemId(position: Int): Long {
       return 0
    }

    override fun getCount(): Int {
        return ambientes.size
    }
}