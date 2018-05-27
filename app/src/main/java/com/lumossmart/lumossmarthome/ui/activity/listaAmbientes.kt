package com.lumossmart.lumossmarthome.ui.activity


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.Ambiente
import com.lumossmart.lumossmarthome.ui.adapter.ListaAmbientesAdapter
import kotlinx.android.synthetic.main.fragment_lista_ambientes.*
import kotlinx.android.synthetic.main.fragment_lista_ambientes.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
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

        var ambientes = listOf(
                Ambiente("@android:color/holo_green_dark", "Quarto Principal","@drawable/bed_empty"),
                Ambiente("@android:color/holo_orange_light","Cozinha", "@drawable/fridge"),
                Ambiente("@android:color/holo_blue_dark","Sala de Estar", "@drawable/sofa"),
                Ambiente("@android:color/holo_orange_dark","Banheiro","@drawable/toilet"),
                Ambiente( "@android:color/holo_red_dark","Sala de Jantar","@drawable/glass_wine"),
                Ambiente("@android:color/holo_purple","Garagem","@drawable/car"),
                Ambiente("@android:color/holo_blue_bright","Area de Serviço","@drawable/washing_machine"),
                Ambiente( "@android:color/holo_green_light","Escritorio","@drawable/laptop_chromebook"),
                Ambiente("@android:color/holo_green_dark", "Quarto Crianças","@drawable/bed_empty"),
                Ambiente("@android:color/holo_orange_light","Cozinha 2", "@drawable/fridge"),
                Ambiente("@android:color/holo_blue_dark","Sala de Estar 2", "@drawable/sofa"),
                Ambiente("@android:color/holo_orange_dark","Banheiro 2","@drawable/toilet"),
                Ambiente( "@android:color/holo_red_dark","Sala de Jantar 2","@drawable/glass_wine"),
                Ambiente("@android:color/holo_purple","Garagem 2","@drawable/car"),
                Ambiente("@android:color/holo_blue_bright","Area de Serviço 2","@drawable/washing_machine"),
                Ambiente( "@android:color/holo_green_light","Escritorio 2","@drawable/laptop_chromebook"))
        Log.e("bib", view.lista_ambientes_listview.toString())
        view.lista_ambientes_listview?.adapter = ListaAmbientesAdapter(ambientes, context)

        return view
    }



}
