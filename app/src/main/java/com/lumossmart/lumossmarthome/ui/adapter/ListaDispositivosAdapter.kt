package com.lumossmart.lumossmarthome.ui.adapter

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.firebase.database.FirebaseDatabase
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.Dispositivo
import kotlinx.android.synthetic.main.item_dispositivo.view.*

class ListaDispositivosAdapter(private val dispositivos: List<Dispositivo>,
                               private val contexto: Context?,
                               private val btnEnable: Boolean) : BaseAdapter(){

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(contexto).inflate(R.layout.item_dispositivo, parent, false)

        val dispositivo = dispositivos[position]

        if(dispositivo.nome == ""){
            viewCriada.card.visibility = View.INVISIBLE
            return viewCriada
        }
        viewCriada.item_dispositivo_nome.text = dispositivo.nome

        viewCriada.item_dispositivo_cor.setImageDrawable(contexto!!.getDrawableByName(dispositivo.cor))

        viewCriada.item_dispositivo_icone.setImageDrawable(contexto!!.getDrawableByName(dispositivo.icone))

        viewCriada.onOff.isChecked = dispositivo.ligado


        viewCriada.onOff.setOnCheckedChangeListener { buttonView, isChecked ->
            val mDatabaseDevices = FirebaseDatabase.getInstance().getReference("casa/dispositivos")
            mDatabaseDevices.child(dispositivo.id).child("ligado").setValue(isChecked)
        }
        if(!btnEnable) {
            viewCriada.onOff.visibility = View.INVISIBLE
        }

        return viewCriada
    }

    override fun getItem(position: Int): Dispositivo {
        return dispositivos[position]
    }

    override fun getItemId(position: Int): Long {
       return 0
    }

    override fun getCount(): Int {
        return dispositivos.size
    }
}