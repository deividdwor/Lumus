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
import com.lumossmart.lumossmarthome.model.Momento
import com.lumossmart.lumossmarthome.ui.adapter.ListaMomentoAdapter
import kotlinx.android.synthetic.main.fragment_lista_momentos.view.*
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView



class ListaMomentos : Fragment() {

    private lateinit var dispositivos: MutableList<Momento>

    companion object {
        fun newInstance(): ListaMomentos {
            return ListaMomentos()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val inflate = inflater.inflate(R.layout.fragment_lista_momentos, container, false)

        val mDatabase = FirebaseDatabase.getInstance().getReference("casa/momentos")

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {  }

            override fun onDataChange(p0: DataSnapshot?) {
                dispositivos = mutableListOf()
                if(p0!!.exists()){
                    for(a in p0.children){
                        var momento = a.getValue(Momento::class.java)
                        momento!!.id = a.key
                        dispositivos.add(momento!!)
                    }
                }
                val p = Momento()
                dispositivos.add(p)
                var listaMomentoAdapter = ListaMomentoAdapter(dispositivos, context)

                inflate.lista.adapter =listaMomentoAdapter
            }

        })

        val mLayoutManager = GridLayoutManager(context, 2)
        inflate.lista.setLayoutManager(mLayoutManager)
        return inflate
    }


}
