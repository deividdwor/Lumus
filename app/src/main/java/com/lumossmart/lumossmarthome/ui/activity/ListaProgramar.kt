package com.lumossmart.lumossmarthome.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.Programar
import com.lumossmart.lumossmarthome.ui.adapter.ListaProgramarAdapter
import kotlinx.android.synthetic.main.fragment_lista_programar.view.*

class ListaProgramar : Fragment() {

    private lateinit var programacoes: MutableList<Programar>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_lista_programar, container, false)
        // Inflate the layout for this fragment
        val mDatabase = FirebaseDatabase
                .getInstance().getReference("casa/programacao")

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {  }

            override fun onDataChange(p0: DataSnapshot?) {
                programacoes = mutableListOf()
                if(p0!!.exists()){
                    for(a in p0.children){
                        var programar = a.getValue(Programar::class.java)
                        programar!!.id = a.key
                        programacoes.add(programar!!)
                    }
                }
                val p = Programar()
                programacoes.add(p)
                var listaProgramarAdapter = ListaProgramarAdapter(programacoes, context)

                view.listaProgramar.adapter = listaProgramarAdapter
            }

        })
        return view
    }

    companion object {
        fun newInstance(): ListaProgramar {
            return ListaProgramar()
        }
    }
}
