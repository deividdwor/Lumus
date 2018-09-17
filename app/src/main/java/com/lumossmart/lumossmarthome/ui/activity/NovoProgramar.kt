package com.lumossmart.lumossmarthome.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.Ambiente
import com.lumossmart.lumossmarthome.model.Dispositivo
import com.lumossmart.lumossmarthome.model.Programar
import com.lumossmart.lumossmarthome.ui.adapter.ListaAmbientesAdapter
import com.lumossmart.lumossmarthome.ui.adapter.ListaDispositivosAdapter
import kotlinx.android.synthetic.main.fragment_novo_dispositivo.view.*
import kotlinx.android.synthetic.main.fragment_novo_programar.view.*
import java.util.*

class NovoProgramar : Fragment() {



    private lateinit var ambientes: MutableList<Ambiente>
    private lateinit var dispositivos: MutableList<Dispositivo>
    private var programar = Programar()
    private  var ambiente: Ambiente? = null

    companion object {
        fun newInstance():NovoProgramar {
            return NovoProgramar()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val inflate = inflater.inflate(R.layout.fragment_novo_programar, container, false)
        inflate.data.isEnabled = false
        inflate.hora.isEnabled = false

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hora = c.get(Calendar.HOUR)
        val minuto = c.get(Calendar.MINUTE)

      inflate.btnCal.setOnClickListener{
          val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, myear, mmonth, mday ->
              var sDay: String = "" + mday
              var sMont: String = "" + mmonth
              if(mday < 10){
                  sDay = "0" + sDay
              }
              if(mmonth < 10){
                  sMont = "0" + sMont
              }
              val data = "" + sDay + "/" + sMont + "/" + myear
              inflate.data.setText(data)
              programar.data = data
          },year , month, day)
          dpd.show()
      }

        inflate.btnHour.setOnClickListener{
            val dpd = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                var sHora: String = "" + hourOfDay
                var sMinute: String = "" + minute
                if(hourOfDay < 10){
                    sHora = "0" + sHora
                }
                if(minute < 10){
                    sMinute = "0" + sMinute
                }
                val hora = "" + sHora + ":" + sMinute

                inflate.hora.setText(hora)
                programar.hora = hora
            },hora , minuto, true)
            dpd.show()
        }



        var mDatabase = FirebaseDatabase.getInstance().getReference("casa/ambiente")

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(p0: DataSnapshot?) {
                ambientes = mutableListOf()
                if (p0!!.exists()) {
                    for (a in p0.children) {
                        ambiente = a.getValue(Ambiente::class.java)
                        ambiente!!.id = a.key
                        ambientes.add(ambiente!!)
                    }
                }
                var listaAmbientesAdapter = ListaAmbientesAdapter(ambientes, context)

                inflate.programarAmbientesSpinner.adapter = listaAmbientesAdapter
            }

        })

        inflate.programarAmbientesSpinner.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: Ambiente = inflate.programarAmbientesSpinner.selectedItem as Ambiente
                programar.ambienteNome = item.nome
                programar.ambienteIcone = item.icone
                mDatabase = FirebaseDatabase.getInstance().getReference("casa/dispositivos")
                mDatabase.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {  }

                    override fun onDataChange(p0: DataSnapshot?) {
                        dispositivos = mutableListOf()
                        if(p0!!.exists()){
                            for(a in p0.children){
                                var dispositivo = a.getValue(Dispositivo::class.java)
                                dispositivo!!.id = a.key
                                if(dispositivo.ambienteKey == item.id){
                                    dispositivos.add(dispositivo!!)
                                }

                            }
                        }
                        inflate.programarDispositivosSpinner?.adapter = ListaDispositivosAdapter(dispositivos, context, false, false)
                    }

                })
            }

        }

        inflate.programarDispositivosSpinner.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: Dispositivo = inflate.programarDispositivosSpinner.selectedItem as Dispositivo
                programar.dispositivoKey = item.id
                programar.DispositivoNome = item.nome
                programar.DispositivoIcone = item.icone

            }
        }
        inflate.repetir.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                inflate.dias.visibility = View.VISIBLE
                inflate.data.visibility = View.INVISIBLE
                inflate.btnCal.visibility = View.INVISIBLE
                programar.repetir = true
            }else{
                inflate.dias.visibility = View.INVISIBLE
                inflate.data.visibility = View.VISIBLE
                inflate.btnCal.visibility = View.VISIBLE
            }
        }

        inflate.cardSegunda.setOnClickListener {view ->
            programar.dias.seg = !programar.dias.seg

            if(programar.dias.seg){
                inflate.cardSegunda.setCardBackgroundColor(Color.WHITE)
            }else{
                inflate.cardSegunda.setCardBackgroundColor(Color.TRANSPARENT)
            }

        }
        inflate.cardTerca.setOnClickListener {view ->
            programar.dias.ter = !programar.dias.ter

            if(programar.dias.ter){
                inflate.cardTerca.setCardBackgroundColor(Color.WHITE)
            }else{
                inflate.cardTerca.setCardBackgroundColor(Color.TRANSPARENT)
            }
        }
        inflate.cardQuarta.setOnClickListener {view ->
            programar.dias.qua= !programar.dias.qua

            if(programar.dias.qua){
                inflate.cardQuarta.setCardBackgroundColor(Color.WHITE)
            }else{
                inflate.cardQuarta.setCardBackgroundColor(Color.TRANSPARENT)
            }
        }
        inflate.cardQuinta.setOnClickListener {view ->
            programar.dias.qui = !programar.dias.qui

            if(programar.dias.qui){
                inflate.cardQuinta.setCardBackgroundColor(Color.WHITE)
            }else{
                inflate.cardQuinta.setCardBackgroundColor(Color.TRANSPARENT)
            }
        }
        inflate.cardSexta.setOnClickListener {view ->
            programar.dias.sex = !programar.dias.sex

            if(programar.dias.sex){
                inflate.cardSexta.setCardBackgroundColor(Color.WHITE)
            }else{
                inflate.cardSexta.setCardBackgroundColor(Color.TRANSPARENT)
            }
        }
        inflate.cardSabado.setOnClickListener {view ->
            programar.dias.sab = !programar.dias.sab

            if(programar.dias.sab){
                inflate.cardSabado.setCardBackgroundColor(Color.WHITE)
            }else{
                inflate.cardSabado.setCardBackgroundColor(Color.TRANSPARENT)
            }
        }
        inflate.cardDomingo.setOnClickListener {view ->
            programar.dias.dom = !programar.dias.dom

            if(programar.dias.dom){
                inflate.cardDomingo.setCardBackgroundColor(Color.WHITE)
            }else{
                inflate.cardDomingo.setCardBackgroundColor(Color.TRANSPARENT)
            }
        }

        inflate.acao.setOnClickListener { v ->
            programar.acao = inflate.acao.isActivated
        }

        inflate.btnSalvaProgramar.setOnClickListener { view ->
            val mDatabase = FirebaseDatabase.getInstance().getReference("casa/programacao")
            val programacaoId = mDatabase.push().key

            mDatabase.child(programacaoId).setValue(programar)

            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, ListaProgramar.newInstance(), "listaProgramar")
                    .commit()
        }
        return inflate
    }
}
