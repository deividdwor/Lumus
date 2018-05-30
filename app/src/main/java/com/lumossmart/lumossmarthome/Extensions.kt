package com.lumossmart.lumossmarthome

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.getDrawableByName(name: String): Drawable{

    var resources = this.resources
    val idIcone = resources.getIdentifier(name, "drawable", this.packageName)
    return resources.getDrawable(idIcone, null)
}