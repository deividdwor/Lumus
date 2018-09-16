package com.lumossmart.lumossmarthome.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Momento (var nome: String = "",
               var icone: String = "",
               var cor: String = "",
               var ligado: Boolean = false,
               var dispositivos: MutableList<String> = mutableListOf(),
               @get:Exclude var id: String = ""
                ){


}