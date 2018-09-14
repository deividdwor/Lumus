package com.lumossmart.lumossmarthome.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Dias (val seg: Boolean = false,
            val ter: Boolean = false,
            val qua: Boolean = false,
            val qui: Boolean = false,
            val sex: Boolean = false,
            val sab: Boolean = false,
            val dom: Boolean = false){}