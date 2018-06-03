package com.lumossmart.lumossmarthome.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Dispositivo (val cor: String = "",
                   val nome: String = "",
                   val icone: String = "",
                   val ambienteKey: String = "",
                   val ligado: Boolean = false,
                   @get:Exclude var id: String = ""
                ){


}