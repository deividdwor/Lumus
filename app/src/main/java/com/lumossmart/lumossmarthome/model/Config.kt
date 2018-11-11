package com.lumossmart.lumossmarthome.model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
class Config (var rede: String = "",
              var senha: String = ""
): Serializable