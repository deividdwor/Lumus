package com.lumossmart.lumossmarthome.ui.adapter

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.nomeAmbienteEnum
import kotlinx.android.synthetic.main.item_nome.view.*

class NomeAmbienteAdapter (private val nomes: List<String>,
                           private val contexto: Context?) : BaseAdapter(){


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(contexto).inflate(R.layout.item_nome, parent, false)

        val nome = nomes[position]

        viewCriada.item_nome.text = nome

        return viewCriada
    }

    override fun getItem(position: Int): String {
        return nomes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return nomes.size
    }
}