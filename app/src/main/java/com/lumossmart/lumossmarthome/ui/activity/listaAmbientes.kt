package com.lumossmart.lumossmarthome.ui.activity


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
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
import com.lumossmart.lumossmarthome.ui.adapter.ListaAmbientesAdapter
import kotlinx.android.synthetic.main.fragment_lista_ambientes.view.*

private lateinit var ambientes: MutableList<Ambiente>

class listaAmbientes : Fragment() {

companion object {
    fun newInstance():listaAmbientes {
        return listaAmbientes()
    }
}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lista_ambientes, container, false)

        val mDatabase = FirebaseDatabase.getInstance().getReference("casa/ambiente")

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {  }

            override fun onDataChange(p0: DataSnapshot?) {
                ambientes = mutableListOf()
                if(p0!!.exists()){
                    for(a in p0.children){
                        var ambiente = a.getValue(Ambiente::class.java)
                        ambiente!!.id = a.key
                        ambientes.add(ambiente!!)
                    }
                }
                view.lista_ambientes_listview?.adapter = ListaAmbientesAdapter(ambientes, context)
            }

        })

        view.lista_ambientes_listview.onItemClickListener = AdapterView.OnItemClickListener{adapterView, view, i, l ->
            val key: String = ambientes.get(i).id
            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, DetalhesAmbiente.newInstance(key), "DetalhesAmbiente")
                    .commit()
        }

        return view
    }



}
