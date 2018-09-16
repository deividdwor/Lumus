package com.lumossmart.lumossmarthome.ui.activity

import android.app.AlertDialog
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
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.*
import com.lumossmart.lumossmarthome.ui.adapter.*
import kotlinx.android.synthetic.main.fragment_novo_ambiente.view.*
import kotlinx.android.synthetic.main.fragment_novo_momento.view.*
import kotlinx.android.synthetic.main.fragment_novo_programar.*
import kotlinx.android.synthetic.main.modal_momento_dispositivo.view.*

private lateinit var ambientes: MutableList<Ambiente>
private lateinit var dialogDispositivos: MutableList<Dispositivo>
private  var ambiente = Ambiente()
private  var momento = Momento()
private var dispositoSelecionado = Dispositivo()

class NovoMomento : Fragment() {

    private lateinit var dispositivos: MutableList<Dispositivo>

    companion object {
        fun newInstance(): NovoMomento {
            return  NovoMomento()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        dispositivos = mutableListOf()
        dialogDispositivos = mutableListOf()
        ambientes = mutableListOf()
        val cores = CorEnum.values()
        val icones = IconeAmbienteEnum.values()
        val nomes = nomeAmbienteEnum.values()

        val inflate = inflater.inflate(R.layout.fragment_novo_momento, container, false)
        inflate.momentoCor.adapter = CorAdapter(cores,  context)
        inflate.momentoIcone.adapter = IconeAmbienteAdapter(icones, context!!.getDrawableByName("@android:color/holo_green_dark"), context)
        inflate.momentoNome.adapter = NomeAmbienteAdapter(nomes, context)

        inflate.btnAddDispositivo.setOnClickListener { view ->
            val dialog = LayoutInflater.from(context).inflate(R.layout.modal_momento_dispositivo, null)


            var mDatabase = FirebaseDatabase.getInstance().getReference("casa/ambiente")

            mDatabase.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {}

                override fun onDataChange(p0: DataSnapshot?) {
                    ambientes = mutableListOf()
                    if (p0!!.exists()) {
                        for (a in p0.children) {
                            ambiente = a.getValue(Ambiente::class.java)!!
                            ambiente!!.id = a.key
                            ambientes.add(ambiente)
                        }
                    }
                    var listaAmbientesAdapter = ListaAmbientesAdapter(ambientes, context)

                    dialog.momentoDialogAmbientes.adapter = listaAmbientesAdapter
                }

            })
            dialog.momentoDialogAmbientes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val item: Ambiente = dialog.momentoDialogAmbientes.selectedItem as Ambiente

                    mDatabase = FirebaseDatabase.getInstance().getReference("casa/dispositivos")
                    mDatabase.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {}

                        override fun onDataChange(p0: DataSnapshot?) {
                            dialogDispositivos = mutableListOf()
                            if (p0!!.exists()) {
                                for (a in p0.children) {
                                    var dispositivo = a.getValue(Dispositivo::class.java)
                                    dispositivo!!.id = a.key
                                    if (dispositivo.ambienteKey == item.id) {
                                        dialogDispositivos.add(dispositivo!!)
                                    }

                                }
                            }
                            dialog.momentoDialogDispositivo?.adapter = ListaDispositivosAdapter(dialogDispositivos, context, true)
                        }

                    })
                }

            }

            dialog.momentoDialogDispositivo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val item: Dispositivo = dialog.momentoDialogDispositivo.selectedItem as Dispositivo
                    dispositoSelecionado = item
                }
            }

            val builder = AlertDialog.Builder(context).setView(dialog)

            val mBuider = builder.show()

            dialog.btnCancelar.setOnClickListener { v ->
                mBuider.dismiss()
            }

            dialog.btnAdicionar.setOnClickListener { v ->
                    momento.dispositivos.add(dispositoSelecionado.id)
                    dispositivos.add(dispositoSelecionado)
                    inflate.momentoListaDispositivos.adapter = ListaDispositivosAdapter(dispositivos, context, false)
                mBuider.dismiss()
            }
        }

        inflate.button2.setOnClickListener { v ->
            momento.nome = inflate.momentoNome.selectedItem as String
            val cor = inflate.momentoCor.selectedItem as CorEnum
            val icone = inflate.momentoIcone.selectedItem as IconeAmbienteEnum
            momento.cor = cor.cor
            momento.icone = icone.icone

            val mDatabase = FirebaseDatabase.getInstance().getReference("casa/momentos")
            val momentoId = mDatabase.push().key

            mDatabase.child(momentoId).setValue(momento)

            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, ListaMomentos.newInstance(), "listaMomentos")
                    .commit()
        }



        inflate.momentoListaDispositivos.adapter = ListaDispositivosAdapter(dispositivos, context, false)
        return inflate

    }
}
