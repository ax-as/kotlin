package com.github.ax_as.dataserpro

import org.jetbrains.kotlinx.dataframe.DataFrame

interface DF {
    val dataframe: DataFrame<*>
}