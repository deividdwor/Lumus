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
import com.lumossmart.lumossmarthome.model.Ambiente
import com.lumossmart.lumossmarthome.model.Dispositivo
import com.lumossmart.lumossmarthome.ui.adapter.*
import kotlinx.android.synthetic.main.activity_main.view.*


class PermissoesLista : Fragment() {

    companion object {
        fun newInstance():PermissoesLista {
            return PermissoesLista()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var ambientes: MutableList<Ambiente> = mutableListOf()

        // Inflate the layout for this fragment
        var inflate = inflater.inflate(R.layout.activity_main, container, false)

        val mDatabaseAmb = FirebaseDatabase.getInstance().getReference("casa/ambiente")

        mDatabaseAmb.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {  }

            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    for(a in p0.children){
                    var ambiente = a.getValue(Ambiente::class.java)
                    ambiente!!.id = a.key
                    ambientes.add(ambiente!!)

                    }
                }
            }

        })

        val mDatabaseDevices = FirebaseDatabase.getInstance().getReference("casa/dispositivos")
        mDatabaseDevices.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {  }

            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    for(a in p0.children){
                        var dispositivo = a.getValue(Dispositivo::class.java)
                        ambientes.filter { i -> i.id == dispositivo!!.ambienteKey }.first().dispositivos.add(dispositivo!!)

                    }

                    var expandableListTitle: List<Ambiente> = mutableListOf()
                    var expandableListDetail: HashMap<Ambiente, List<Dispositivo>> = hashMapOf()
                    for(a in ambientes){
                        expandableListDetail[a] = a.dispositivos
                    }
                    expandableListTitle = expandableListDetail.keys.toList()

                    var expandableAdapter = ExpandableAdapter(context, expandableListTitle, expandableListDetail)
                    inflate.expandableListView.setAdapter(expandableAdapter)
                }
            }

        })





        return inflate
    }


}
