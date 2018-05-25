package com.lumossmart.lumossmarthome.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.Ambiente
import kotlinx.android.synthetic.main.item_ambiente.view.*

class ListaAmbientesAdapter(private val ambientes: List<Ambiente>,
                            private val contexto: Context?) : BaseAdapter(){

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(contexto).inflate(R.layout.item_ambiente, parent, false)

        val ambiente = ambientes[position]
        viewCriada.item_ambiente_nome.text = ambiente.nome

        var resources = contexto!!.resources
        val idCor = resources.getIdentifier(ambiente.cor, "drawable", contexto!!.packageName)
        var drawable = resources.getDrawable(idCor)
        viewCriada.item_ambiente_cor.setImageDrawable(drawable)

        val idIcone = resources.getIdentifier(ambiente.icone, "drawable", contexto!!.packageName)
        var drawableIcone = resources.getDrawable(idIcone)
        viewCriada.item_ambiente_icone.setImageDrawable(drawableIcone)


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