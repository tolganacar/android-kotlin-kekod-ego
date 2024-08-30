package com.tolganacar.kekodego.utils

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions

fun NavController.navigateSafe(menuItem: Int) {
    val navOptions = NavOptions.Builder()
        .setLaunchSingleTop(true)
        .setPopUpTo(graph.findStartDestination().id, inclusive = false, saveState = true)
        .setRestoreState(true)
        .build()

    navigate(menuItem, null, navOptions)
}