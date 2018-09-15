package com.lumossmart.lumossmarthome.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Dias (var seg: Boolean = false,
            var ter: Boolean = false,
            var qua: Boolean = false,
            var qui: Boolean = false,
            var sex: Boolean = false,
            var sab: Boolean = false,
            var dom: Boolean = false){}