package com.lumossmart.lumossmarthome.ui.activity

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.Ambiente
import com.lumossmart.lumossmarthome.model.Dispositivo
import com.lumossmart.lumossmarthome.ui.adapter.ListaAmbientesAdapter
import com.lumossmart.lumossmarthome.ui.adapter.ListaDispositivosAdapter
import kotlinx.android.synthetic.main.fragment_detalhes_ambiente.view.*
import kotlinx.android.synthetic.main.fragment_lista_ambientes.view.*


private  val ambiente: Ambiente? = null
private lateinit var dispositivos: MutableList<Dispositivo>

class DetalhesAmbiente() : Fragment() {

    companion object {
        fun newInstance(key: String):DetalhesAmbiente {
            val args = Bundle()
            args.putString("key", key)
            val detalhesAmbiente = DetalhesAmbiente()
            detalhesAmbiente.arguments = args
            return detalhesAmbiente
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mDatabase = FirebaseDatabase.getInstance().getReference("casa/ambiente")

        val key = arguments!!.getString("key")

        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_detalhes_ambiente, container, false)
        mDatabase.child(key).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    val ambiente = p0!!.getValue(Ambiente::class.java)
                    inflate.nomeAmbiente.text = ambiente!!.nome

                    inflate.corAmbiente.setImageDrawable(context!!.getDrawableByName(ambiente.cor))
                    inflate.fundoIcone.setImageDrawable(context!!.getDrawableByName(ambiente.cor))
                    inflate.corFundoLapis.setImageDrawable(context!!.getDrawableByName(ambiente.cor))
                    inflate.iconeAmbiente.setImageDrawable(context!!.getDrawableByName(ambiente.icone))

                }

            }

        })

        val mDatabaseDevices = FirebaseDatabase.getInstance().getReference("casa/dispositivos")

        mDatabaseDevices.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {  }

            override fun onDataChange(p0: DataSnapshot?) {
                dispositivos = mutableListOf()
                if(p0!!.exists()){
                    for(a in p0.children){
                        var dispositivo = a.getValue(Dispositivo::class.java)
                        dispositivo!!.id = a.key
                        if(dispositivo.ambienteKey == key){
                            dispositivos.add(dispositivo!!)
                        }

                    }
                }
                inflate.dispositivosListView?.adapter = ListaDispositivosAdapter(dispositivos, context)
            }

        })
        return inflate
    }

}
