package com.lumossmart.lumossmarthome.model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
class Permissao (val dispositivo: String,
                 val autorizado: Boolean = false
                ):Serializable