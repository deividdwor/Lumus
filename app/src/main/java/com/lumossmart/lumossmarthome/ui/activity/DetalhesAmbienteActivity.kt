package com.lumossmart.lumossmarthome.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.Ambiente
import com.lumossmart.lumossmarthome.ui.adapter.ListaAmbientesAdapter
import kotlinx.android.synthetic.main.activity_lista_ambientes.*

class DetalhesAmbienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_ambiente)

        val ambientes = listOf(
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

        lista_ambientes_listview.adapter = ListaAmbientesAdapter(ambientes, this)
    }
}
