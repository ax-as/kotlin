package com.github.ax_as.dataserpro

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readCSV
import org.jetbrains.kotlinx.dataframe.io.readDelimStr
import java.util.Locale


class DtpDataFrame(private val df: DataFrame<*>) : DF {

    companion object {

        const val nome = "Nome"
        const val matric = "Matric"
        const val cargo = "Cargo"
        const val uf = "UF"
        const val lot = "Lot"
        const val admissao = "Admissão"
        const val vinculo = "Vínculo"
        const val cpf = "CPF"
        const val nivel = "Nível"

        fun fromCsvFile(filepath: String): DtpDataFrame {
            val df = preprocess(DataFrame.readCSV(filepath, parserOptions = ParserOptions(
                dateTimePattern = "dd/MM/uuuu"
            )))
            return DtpDataFrame(df)
        }

        fun fromText(text: String): DtpDataFrame {
            val df = preprocess(DataFrame.readDelimStr(text))
            return DtpDataFrame(df)
        }

        private fun preprocess(df: DataFrame<*>): DataFrame<*> {
            return df.head(Int.MAX_VALUE)
                .select(
                    nome,
                    uf,
                    lot,
                    nivel,
                    admissao,
                    cargo,
                )
                .update(nome) { it.toString().uppercase() }
        }
    }

    fun print(rowslimite: Int = Int.MAX_VALUE) {
        this.df.print(rowsLimit = rowslimite, valueLimit = Int.MAX_VALUE)
    }

    override val dataframe: DataFrame<*>
        get() = df

}