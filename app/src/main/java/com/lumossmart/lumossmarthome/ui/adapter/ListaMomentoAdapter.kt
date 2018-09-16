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
import com.lumossmart.lumossmarthome.model.Momento
import com.lumossmart.lumossmarthome.model.Programar
import kotlinx.android.synthetic.main.item_momento.view.*
import kotlinx.android.synthetic.main.item_programar.view.*

class ListaMomentoAdapter(private val momentos: List<Momento>,
                          private val contexto: Context?) : BaseAdapter(){

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(contexto).inflate(R.layout.item_momento, parent, false)

        val momento = momentos[position]

        if(momento.nome == ""){
            view.momentoCard.visibility = View.INVISIBLE

            return view
        }
        view.momentoNome.text = momento.nome
        view.momentoIcone.setImageDrawable(contexto!!.getDrawableByName(momento.icone))
        view.momentoOnOff.isChecked = momento.ligado

        view.momentoOnOff.setOnCheckedChangeListener { _, isChecked ->
            val mDatabaseDevices = FirebaseDatabase.getInstance().getReference("casa/momentos")
            mDatabaseDevices.child(momento.id).child("ligado").setValue(isChecked)
        }

        return view
    }

    override fun getItem(position: Int): Momento {
        return momentos[position]
    }

    override fun getItemId(position: Int): Long {
       return 0
    }

    override fun getCount(): Int {
        return momentos.size
    }
}