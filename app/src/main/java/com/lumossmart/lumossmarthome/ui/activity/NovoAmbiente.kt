package com.lumossmart.lumossmarthome.ui.activity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.CorEnum
import com.lumossmart.lumossmarthome.model.IconeEnum
import com.lumossmart.lumossmarthome.ui.adapter.ItemIconeAdapter
import com.lumossmart.lumossmarthome.ui.adapter.ItemSpinnerAdapter
import kotlinx.android.synthetic.main.fragment_novo_ambiente.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NovoAmbiente : Fragment(){

    companion object {
        fun newInstance():NovoAmbiente {
            return NovoAmbiente()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_novo_ambiente, container, false)

        val cores = CorEnum.values()
        val icones = IconeEnum.values()
        val nomes = listOf<String>("Quarto Casal", "Banheiro Principal" ,"Garagem")

        inflate.corAmbiente.adapter = ItemSpinnerAdapter(cores,  context)
        inflate.iconeAmbiente.adapter = ItemIconeAdapter(icones, context)
        val dataAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nomes)
        inflate.nomeAmbienteSpinner.adapter = dataAdapter
        return inflate
    }


}
