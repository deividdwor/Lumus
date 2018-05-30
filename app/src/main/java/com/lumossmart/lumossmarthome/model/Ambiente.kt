package com.lumossmart.lumossmarthome.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Ambiente (val cor: String = "",
                val nome: String = "",
                val icone: String = "",
                @get:Exclude var id: String = ""
                ){


}