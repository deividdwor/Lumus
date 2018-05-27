package com.lumossmart.lumossmarthome.ui.activity


import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
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
import kotlinx.android.synthetic.main.item_ambiente.view.*


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

        inflate.nomeAmbienteSpinner.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: String = inflate.nomeAmbienteSpinner.selectedItem as String
                val item_ambiente = inflate.item_ambiente
                item_ambiente.item_ambiente_nome.text = item
            }

        }

        inflate.corAmbiente.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: CorEnum = inflate.corAmbiente.selectedItem as CorEnum

                val idIcone = resources.getIdentifier(item.cor, "drawable", context!!.packageName)
                var drawableIcone = resources.getDrawable(idIcone, null)
                val item_ambiente = inflate.item_ambiente
                item_ambiente.item_ambiente_cor.setImageDrawable(drawableIcone)
            }

        }

        inflate.iconeAmbiente.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: IconeEnum = inflate.iconeAmbiente.selectedItem as IconeEnum

                val idIcone = resources.getIdentifier(item.icone, "drawable", context!!.packageName)
                var drawableIcone = resources.getDrawable(idIcone, null)
                val item_ambiente = inflate.item_ambiente
                item_ambiente.item_ambiente_icone.setImageDrawable(drawableIcone)
            }

        }
        return inflate
    }


}
