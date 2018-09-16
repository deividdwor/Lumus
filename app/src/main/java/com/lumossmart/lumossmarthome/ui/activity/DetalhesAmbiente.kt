package com.lumossmart.lumossmarthome.ui.activity

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
import android.widget.CompoundButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.Ambiente
import com.lumossmart.lumossmarthome.model.Dispositivo
import com.lumossmart.lumossmarthome.ui.adapter.ListaDispositivosAdapter
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_menu.view.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.view.*
import kotlinx.android.synthetic.main.fragment_detalhes_ambiente.view.*
import kotlinx.android.synthetic.main.item_dispositivo.view.*


private  val ambiente: Ambiente? = null
private lateinit var dispositivos: MutableList<Dispositivo>

class DetalhesAmbiente() : Fragment() {

    companion object {
        fun newInstance(ambiente: Ambiente):DetalhesAmbiente {
            val args = Bundle()
            args.putSerializable("ambiente", ambiente)
            val detalhesAmbiente = DetalhesAmbiente()
            detalhesAmbiente.arguments = args
            return detalhesAmbiente
        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val amb: Ambiente = arguments!!.get("ambiente") as Ambiente

        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_detalhes_ambiente, container, false)
        inflate.nomeAmbiente.text = amb.nome
        inflate.corAmbiente.setImageDrawable(context!!.getDrawableByName(amb.cor))
        inflate.fundoIcone.setImageDrawable(context!!.getDrawableByName(amb.cor))
        inflate.iconeAmbiente.setImageDrawable(context!!.getDrawableByName(amb.icone))

        val mDatabaseDevices = FirebaseDatabase.getInstance().getReference("casa/dispositivos")

        mDatabaseDevices.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {  }

            override fun onDataChange(p0: DataSnapshot?) {
                dispositivos = mutableListOf()
                if(p0!!.exists()){
                    for(a in p0.children){
                        var dispositivo = a.getValue(Dispositivo::class.java)
                        dispositivo!!.id = a.key
                        if(dispositivo.ambienteKey == amb.id){
                            dispositivos.add(dispositivo!!)
                        }

                    }
                }
                val d = Dispositivo()
                dispositivos.add(d)
                inflate.dispositivosListView?.adapter = ListaDispositivosAdapter(dispositivos, context, true)
            }

        })

        registerForContextMenu(inflate.dispositivosListView)
        val toolbar = activity!!.toolbar
        toolbar.background = context!!.getDrawableByName(amb.cor)
        return inflate
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu!!.add(0, v!!.id, 0, "Editar")
        menu!!.add(0, v!!.id, 0, "Excluir")
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        super.onContextItemSelected(item)
        val info = item!!.getMenuInfo() as AdapterView.AdapterContextMenuInfo
        val disp = dispositivos.get(info.position)

        if(item.title == "Editar"){

        }
        if (item.title == "Excluir"){
            val mDatabase = FirebaseDatabase.getInstance().getReference("casa/dispositivos")
            val dispositivo = mDatabase.child(disp.id)
            dispositivo.removeValue()
        }
        return true
    }
}
