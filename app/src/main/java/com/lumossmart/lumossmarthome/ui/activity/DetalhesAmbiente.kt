package com.lumossmart.lumossmarthome.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.Ambiente


private  val ambiente: Ambiente? = null

class DetalhesAmbiente() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalhes_ambiente, container, false)
    }

}
