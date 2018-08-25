package com.lumossmart.lumossmarthome.ui.activity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.AdapterView
import com.google.firebase.database.*

import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.Ambiente
import com.lumossmart.lumossmarthome.model.Dispositivo
import com.lumossmart.lumossmarthome.ui.adapter.ListaAmbientesAdapter
import com.lumossmart.lumossmarthome.ui.adapter.ListaDispositivosAdapter
import kotlinx.android.synthetic.main.fragment_detalhes_ambiente.view.*
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

        val mDatabase = FirebaseDatabase
                .getInstance().getReference("casa/ambiente")

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
                var listaAmbientesAdapter = ListaAmbientesAdapter(ambientes, context)

                view.lista_ambientes_listview?.adapter = listaAmbientesAdapter
            }

        })

        view.lista_ambientes_listview.onItemClickListener = AdapterView.OnItemClickListener{adapterView, view, i, l ->
            val key = ambientes.get(i)
            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, DetalhesAmbiente.newInstance(key), "DetalhesAmbiente")
                    .commit()
        }

        registerForContextMenu(view.lista_ambientes_listview)

        return view
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu!!.add(Menu.NONE, v!!.id, Menu.NONE, "Editar")
        menu!!.add(Menu.NONE, v!!.id, Menu.NONE, "Excluir")
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        super.onContextItemSelected(item)
        val info = item!!.getMenuInfo() as AdapterView.AdapterContextMenuInfo
        val amb = ambientes.get(info.position)

        if(item.title == "Editar"){

        }
        if (item.title == "Excluir"){
            val mDatabase = FirebaseDatabase.getInstance().getReference("casa")
                val ambiente = mDatabase.child("ambiente").child(amb.id)
                ambiente.removeValue()
        }
        return true
    }

}
