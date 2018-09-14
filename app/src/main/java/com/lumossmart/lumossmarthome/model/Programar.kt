package com.lumossmart.lumossmarthome.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Programar (val data: String = "",
                 val hora: String = "",
                 val dispositivoKey: String = "",
                 val ligado: Boolean = false,
                 val repetir: Boolean = false,
                 val dias: Dias
                ){


}