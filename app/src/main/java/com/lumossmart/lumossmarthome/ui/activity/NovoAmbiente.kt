package com.lumossmart.lumossmarthome.ui.activity

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.CorEnum
import com.lumossmart.lumossmarthome.model.IconeAmbienteEnum
import com.lumossmart.lumossmarthome.ui.adapter.IconeAmbienteAdapter
import com.lumossmart.lumossmarthome.ui.adapter.CorAdapter
import kotlinx.android.synthetic.main.fragment_novo_ambiente.view.*
import kotlinx.android.synthetic.main.item_ambiente.view.*
import com.google.firebase.database.FirebaseDatabase
import com.lumossmart.lumossmarthome.getDrawableByName
import com.lumossmart.lumossmarthome.model.Ambiente
import com.lumossmart.lumossmarthome.model.nomeAmbienteEnum
import com.lumossmart.lumossmarthome.ui.adapter.NomeAmbienteAdapter
class NovoAmbiente : Fragment(){

    companion object {
        fun newInstance():NovoAmbiente {
            return NovoAmbiente()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_novo_ambiente, container, false)

        val cores = CorEnum.values()
        val icones = IconeAmbienteEnum.values()
        val nomes = nomeAmbienteEnum.values()

        val nomesString = nomes.map { it.nome }.toList()

        inflate.corAmbiente.adapter = CorAdapter(cores,  context)
        inflate.iconeAmbiente.adapter = IconeAmbienteAdapter(icones, context!!.getDrawableByName("@color/amarelo"), context)
        inflate.nomeAmbienteSpinner.adapter = NomeAmbienteAdapter(nomesString, context)

        var idCurrentIcone = 0

        inflate.nomeAmbienteSpinner.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: String = inflate.nomeAmbienteSpinner.selectedItem as String

                inflate.item_ambiente.item_ambiente_nome.text = item
            }

        }

        inflate.corAmbiente.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: CorEnum = inflate.corAmbiente.selectedItem as CorEnum

                inflate.item_ambiente.item_ambiente_cor.setImageDrawable(context!!.getDrawableByName(item.cor))
                inflate.iconeAmbiente.adapter = IconeAmbienteAdapter(icones, context!!.getDrawableByName(item.cor), context)
                inflate.iconeAmbiente.setSelection(idCurrentIcone)
            }

        }

        inflate.iconeAmbiente.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: IconeAmbienteEnum = inflate.iconeAmbiente.selectedItem as IconeAmbienteEnum

                inflate.item_ambiente.item_ambiente_icone.setImageDrawable(context!!.getDrawableByName(item.icone))
                idCurrentIcone = position
            }

        }

        inflate.btnSalvaAmbiente.setOnClickListener { view ->
            val mDatabase = FirebaseDatabase.getInstance().getReference("casa/ambiente")
            // Creating new user node, which returns the unique key value
            // new user node would be /users/$userid/
            val ambienteId = mDatabase.push().key

            val cor = inflate.corAmbiente.selectedItem as CorEnum
            val nome = inflate.nomeAmbienteSpinner.selectedItem as String
            val icone = inflate.iconeAmbiente.selectedItem as IconeAmbienteEnum
            val ambiente = Ambiente(cor.cor, nome, icone.icone)

            // pushing user to 'users' node using the userId
            mDatabase.child(ambienteId).setValue(ambiente)

            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, listaAmbientes.newInstance(), "listaAmbientes")
                    .commit()
        }
        return inflate
    }


}
