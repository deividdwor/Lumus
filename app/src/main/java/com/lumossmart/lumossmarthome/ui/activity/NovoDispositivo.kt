package com.lumossmart.lumossmarthome.ui.activity


import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.google.firebase.database.FirebaseDatabase

import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.*
import com.lumossmart.lumossmarthome.ui.adapter.*
import kotlinx.android.synthetic.main.fragment_novo_dispositivo.view.*
import kotlinx.android.synthetic.main.item_dispositivo.view.*

class NovoDispositivo : Fragment() {

    companion object {
        fun newInstance(ambiente: Ambiente):NovoDispositivo {
            val args = Bundle()
            args.putSerializable("ambiente", ambiente)
            var novoDispositivo = NovoDispositivo()
            novoDispositivo.arguments = args
            return novoDispositivo
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_novo_dispositivo, container, false)

        val cores = CorEnum.values()
        val icones = IconeDispositivoEnum.values()
        val nomes = nomeDispositivoEnum.values()

        inflate.corDispositivo.adapter = CorAdapter(cores, context)
        inflate.iconeDispositivo.adapter = IconeDispositivoAdapter(icones, context!!.getDrawableByName("@android:color/holo_green_dark"), context)
        inflate.nomeDispositivoSpinner.adapter = NomeDispositivoAdapter(nomes, context)

        var idCurrentIcone = 0

        inflate.nomeDispositivoSpinner.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: String = inflate.nomeDispositivoSpinner.selectedItem as String

                inflate.item_dispositivo.item_dispositivo_nome.text = item
            }

        }

        inflate.corDispositivo.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: CorEnum = inflate.corDispositivo.selectedItem as CorEnum

                inflate.item_dispositivo.item_dispositivo_cor.setImageDrawable(context!!.getDrawableByName(item.cor))
                inflate.iconeDispositivo.adapter = IconeDispositivoAdapter(icones, context!!.getDrawableByName(item.cor), context)
                inflate.iconeDispositivo.setSelection(idCurrentIcone)
            }

        }

        inflate.iconeDispositivo.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = inflate.iconeDispositivo.selectedItem as IconeDispositivoEnum

                inflate.item_dispositivo.item_dispositivo_icone.setImageDrawable(context!!.getDrawableByName(item.icone))
                idCurrentIcone = position
            }

        }

        inflate.btnSalvaDispositivo.setOnClickListener { view ->
            val mDatabase = FirebaseDatabase.getInstance().getReference("casa/dispositivos")
            // Creating new user node, which returns the unique key value
            // new user node would be /users/$userid/
            val dispositivoId = mDatabase.push().key

            val cor = inflate.corDispositivo.selectedItem as CorEnum
            val nome = inflate.nomeDispositivoSpinner.selectedItem as String
            val icone = inflate.iconeDispositivo.selectedItem as IconeDispositivoEnum
            val ligado = inflate.onOff.isChecked
            val ambiente = arguments!!.get("ambiente") as Ambiente
            val dispositivo = Dispositivo(cor.cor, nome, icone.icone, ambiente.id, ligado)

            // pushing user to 'users' node using the userId
            mDatabase.child(dispositivoId).setValue(dispositivo)

            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, DetalhesAmbiente.newInstance( ambiente), "DetalhesAmbiente")
                    .commit()
        }


        return inflate
    }


}
