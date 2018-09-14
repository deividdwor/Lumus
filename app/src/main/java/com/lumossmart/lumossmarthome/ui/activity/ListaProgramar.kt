package com.lumossmart.lumossmarthome.ui.activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lumossmart.lumossmarthome.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListaProgramar : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_programar, container, false)
    }

    companion object {
        fun newInstance(): ListaProgramar {
            return ListaProgramar()
        }
    }
}
