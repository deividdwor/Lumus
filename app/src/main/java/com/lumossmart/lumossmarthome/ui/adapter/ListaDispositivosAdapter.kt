package com.lumossmart.lumossmarthome.ui.adapter

import android.content.Context
import android.graphics.Color
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
                               private val btnEnable: Boolean = true,
                               private val btnVisible: Boolean = true,
                               private val btnAdd : Boolean = false) : BaseAdapter(){

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(contexto).inflate(R.layout.item_dispositivo, parent, false)

        val dispositivo = dispositivos[position]


        if(btnAdd){

            viewCriada.item_dispositivo_nome.visibility = View.INVISIBLE

            viewCriada.item_dispositivo_cor.visibility = View.INVISIBLE

            viewCriada.item_dispositivo_icone.visibility = View.INVISIBLE

            viewCriada.onOff.visibility = View.INVISIBLE

            viewCriada.card.setCardBackgroundColor(Color.WHITE)

            viewCriada.btnAdd.visibility = View.VISIBLE

            return viewCriada
        }

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
        if(!btnVisible) {
            viewCriada.onOff.visibility = View.INVISIBLE
        }

        if(!btnEnable){
            viewCriada.onOff.isEnabled = false
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