package com.lumossmart.lumossmarthome.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.Ambiente
import com.lumossmart.lumossmarthome.model.CorEnum
import com.lumossmart.lumossmarthome.model.Dispositivo
import com.lumossmart.lumossmarthome.ui.adapter.IconeAmbienteAdapter
import com.lumossmart.lumossmarthome.ui.adapter.ListaAmbientesAdapter
import com.lumossmart.lumossmarthome.ui.adapter.ListaDispositivosAdapter
import kotlinx.android.synthetic.main.fragment_lista_ambientes.view.*
import kotlinx.android.synthetic.main.fragment_novo_ambiente.view.*
import kotlinx.android.synthetic.main.fragment_novo_dispositivo.view.*
import kotlinx.android.synthetic.main.fragment_novo_programar.view.*
import kotlinx.android.synthetic.main.item_ambiente.view.*
import kotlinx.android.synthetic.main.item_dispositivo.view.*
import java.util.*

class NovoProgramar : Fragment() {


    private lateinit var ambientes: MutableList<Ambiente>
    private  var ambiente: Ambiente? = null
    private lateinit var dispositivos: MutableList<Dispositivo>

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
              inflate.data.setText("" + mday + "/" + mmonth + "/" + myear)
          },year , month, day)
          dpd.show()
      }

        inflate.btnHour.setOnClickListener{
            val dpd = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                inflate.hora.setText("" + hourOfDay + ":" + minute)
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
                        inflate.programarDispositivosSpinner?.adapter = ListaDispositivosAdapter(dispositivos, context)
                    }

                })
            }

        }

        inflate.repetir.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                inflate.dias.visibility = View.VISIBLE
                inflate.data.visibility = View.INVISIBLE
                inflate.btnCal.visibility = View.INVISIBLE
            }else{
                inflate.dias.visibility = View.INVISIBLE
                inflate.data.visibility = View.VISIBLE
                inflate.btnCal.visibility = View.VISIBLE
            }
        }
        return inflate
    }
}
