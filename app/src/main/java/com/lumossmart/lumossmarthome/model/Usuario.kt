package com.lumossmart.lumossmarthome.model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
class Usuario (val uid: String = "",
               val admin: Boolean = false,
               var dispositivos: List<String>
                ):Serializable