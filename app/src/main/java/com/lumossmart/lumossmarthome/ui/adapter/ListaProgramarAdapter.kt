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
import com.lumossmart.lumossmarthome.model.Programar
import kotlinx.android.synthetic.main.item_programar.view.*

class ListaProgramarAdapter(private val programacoes: List<Programar>,
                            private val contexto: Context?) : BaseAdapter(){

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(contexto).inflate(R.layout.item_programar, parent, false)

        val programar = programacoes[position]

        if(programar.hora == ("")){
            view.card.visibility = View.INVISIBLE

            return view
        }
        view.item_dispositivo_nome2.text = programar.DispositivoNome
        view.item_dispositivo_icone2.setImageDrawable(contexto!!.getDrawableByName(programar.DispositivoIcone))

        view.item_dispositivo_nome.text = programar.ambienteNome
        view.item_dispositivo_icone.setImageDrawable(contexto!!.getDrawableByName(programar.ambienteIcone))

        view.hora.text = programar.hora
        view.toggleButton.isChecked = programar.acao
        view.onOff.isChecked = programar.ligado
        view.data.text = programar.data

        if(programar.repetir){
            view.dias.visibility = View.VISIBLE
            view.data.visibility = View.INVISIBLE
        }else{

            view.dias.visibility = View.INVISIBLE
            view.data.visibility = View.VISIBLE
        }
        if(programar.dias.seg){
            view.cardSegunda.setCardBackgroundColor(Color.WHITE)
        }
        if(programar.dias.ter){
            view.cardTerca.setCardBackgroundColor(Color.WHITE)
        }
        if(programar.dias.qua){
            view.cardQuarta.setCardBackgroundColor(Color.WHITE)
        }
        if(programar.dias.qui){
            view.cardQuinta.setCardBackgroundColor(Color.WHITE)
        }
        if(programar.dias.sex){
            view.cardSexta.setCardBackgroundColor(Color.WHITE)
        }
        if(programar.dias.sab){
            view.cardSabado.setCardBackgroundColor(Color.WHITE)
        }
        if(programar.dias.dom){
            view.cardDomingo.setCardBackgroundColor(Color.WHITE)
        }

        view.onOff.setOnCheckedChangeListener { buttonView, isChecked ->
            val mDatabaseDevices = FirebaseDatabase.getInstance().getReference("casa/programacao")
            mDatabaseDevices.child(programar.id).child("ligado").setValue(isChecked)
        }
        return view
    }

    override fun getItem(position: Int): Programar {
        return programacoes[position]
    }

    override fun getItemId(position: Int): Long {
       return 0
    }

    override fun getCount(): Int {
        return programacoes.size
    }
}