package com.lumossmart.lumossmarthome.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase

import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.Config
import kotlinx.android.synthetic.main.fragment_configuracao.view.*

class Configuracao : Fragment() {

    companion object {
        fun newInstance():Configuracao {
            return Configuracao()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val inflate =  inflater.inflate(R.layout.fragment_configuracao, container, false)


        inflate.btnSalvaConfiguracao.setOnClickListener { view ->
            val mDatabase = FirebaseDatabase.getInstance().getReference("casa/config")

            val nomeRede = inflate.txtRede as EditText
            val senha = inflate.txtSenha as EditText


            val config = Config(nomeRede.text.toString(), senha.text.toString())

            val id = mDatabase.push().key

            mDatabase.child(id).setValue(config)

            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, listaAmbientes.newInstance(), "listaAmbientes")
                    .commit()
        }

        return inflate
    }


}
