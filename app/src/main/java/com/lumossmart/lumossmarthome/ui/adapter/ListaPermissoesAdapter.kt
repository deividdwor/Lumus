package com.lumossmart.lumossmarthome.ui.adapter

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListAdapter
import android.widget.SimpleAdapter
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.Ambiente
import com.lumossmart.lumossmarthome.model.Permissao
import kotlinx.android.synthetic.main.fragment_permissoes_lista.view.*
import kotlinx.android.synthetic.main.item_permissao.view.*

class ListaPermissoesAdapter(private val permissoes: List<Ambiente>,
                             private val contexto: Context?) : BaseAdapter(){

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(contexto).inflate(R.layout.item_permissao, parent, false)

        val ambiente = permissoes[position]

        viewCriada.item_ambiente_nome.text = ambiente.nome

       viewCriada.item_ambiente_cor.setImageDrawable(contexto!!.getDrawableByName(ambiente.cor))

       viewCriada.item_ambiente_icone.setImageDrawable(contexto!!.getDrawableByName(ambiente.icone))


        return viewCriada
    }

    override fun getItem(position: Int): Ambiente {
        return permissoes[position]
    }

    override fun getItemId(position: Int): Long {
       return 0
    }

    override fun getCount(): Int {
        return permissoes.size
    }
}