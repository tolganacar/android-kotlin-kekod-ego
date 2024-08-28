package com.tolganacar.kekodego.data

import com.google.android.material.switchmaterial.SwitchMaterial

data class SwitchItem(
    val switch: SwitchMaterial,
    val fragmentId: Int,
    val title: String,
    val iconRes: Int
)