package com.lumossmart.lumossmarthome.ui.activity

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lumossmart.lumossmarthome.R
import kotlinx.android.synthetic.main.modal_momento_dispositivo.view.*

class DialogMomentoDispositivo: DialogFragment() {

    companion object {
        fun newInstance(): DialogMomentoDispositivo {
            return  DialogMomentoDispositivo()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return super.onCreateView(inflater, container, savedInstanceState)


        val inflate = inflater!!.inflate(R.layout.modal_momento_dispositivo, container, false)


        inflate.btnCancelar.setOnClickListener { view ->
            dialog.dismiss()

        }


        return inflate
    }
}