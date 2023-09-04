package com.github.ax_as.dataserpro

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readCSV
import java.util.*

class SerproDataframe(private val df: DataFrame<*>) : DF {
    fun print() {
        df.print()
    }

    companion object {
        const val pos = "pos"
        const val inscricao = "Inscrição"
        const val nome = "Nome"
        const val acertosPortugues = "Acertos Ling. Por."
        const val notaIngles = "Nota Ling. Inglesa"
        const val acertosIngles = "Acertos Ling. Inglesa"
        const val notaEstatistica = "Nota Estatistica"
        const val acertosEstatistica = "Acertos Estatística"
        const val notaRacLogico = "Nota Rac. Lógico"
        const val acertosRacLogic = "Acertos Rac. Lógico"
        const val notaLegislac = "Nota Legislação"
        const val acertosLeg = "Acertos Legislação"
        const val notaCBasicos = "Nota C. Básicos"
        const val acertosCBasicos = "Acertos C. Básicos"
        const val notaCEspecifico = "Nota C. Específicos"
        const val acertosCEspecific = "Acertos C. Específicos"
        const val notaParcial = "Nota Parcial"
        const val notaFinal = "Nota (Cálculo Final)"
        const val notaPort = "Nota Ling. Por."
        const val notaLinguas = "Soma Linguas"

        fun fromCsvFile(filepaths: List<String>): SerproDataframe {
            var dataframe: DataFrame<*>? = null

            for (filepath in filepaths) {
                val csv = DataFrame.readCSV(
                    filepath, parserOptions = ParserOptions(Locale.US)
                )
                dataframe = dataframe?.concat(csv) ?: csv

            }

            val df = preprocess(dataframe!!)
            return SerproDataframe(df)
        }


        private fun preprocess(df: DataFrame<*>): DataFrame<*> {
            return df.head(Int.MAX_VALUE).select(
                pos,
                nome,
                notaCBasicos,
                notaCEspecifico,
                notaParcial,
                notaFinal
            ).update(nome) { it.toString().uppercase() }
        }

    }

    override val dataframe: DataFrame<*>
        get() = df
}