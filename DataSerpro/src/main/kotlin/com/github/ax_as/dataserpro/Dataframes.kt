package com.github.ax_as.dataserpro

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*

class Dataframes {
    companion object {
        fun combine(
            tabela1: DataFrame<*>,
            tabela2: DataFrame<*>
        ): DataFrame<*> {
            return tabela1.join(tabela2, JoinType.Inner) { this["Nome"] }.head(
                Int.MAX_VALUE
            )
        }
    }
}