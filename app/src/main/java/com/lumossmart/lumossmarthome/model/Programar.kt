package com.lumossmart.lumossmarthome.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Programar (var data: String = "",
                 var hora: String = "",
                 var ambienteNome: String = "",
                 var DispositivoNome: String = "",
                 var ambienteIcone: String = "",
                 var DispositivoIcone: String = "",
                 var dispositivoKey: String = "",
                 var ligado: Boolean = true,
                 var acao: Boolean = false,
                 var repetir: Boolean = false,
                 var dias: Dias = Dias(),
                 @get:Exclude var id: String = ""
                ){


}