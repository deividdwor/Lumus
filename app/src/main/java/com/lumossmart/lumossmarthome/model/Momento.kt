package com.lumossmart.lumossmarthome.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Momento (var nome: String = "",
               var icone: String = "",
               var cor: String = "",
               var dispositivos: MutableList<DispositivoSimples> = mutableListOf(),
               @get:Exclude var id: String = ""
                ){


}