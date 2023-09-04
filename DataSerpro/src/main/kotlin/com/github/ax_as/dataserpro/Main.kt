package com.github.ax_as.dataserpro

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.arguments.unique
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.unique
import com.github.ajalt.clikt.parameters.types.path
import org.jetbrains.kotlinx.dataframe.api.distinctBy
import org.jetbrains.kotlinx.dataframe.api.print
import org.jetbrains.kotlinx.dataframe.api.sortByDesc
import java.nio.file.Path
import kotlin.io.path.pathString

fun main(args: Array<String>) {
    val ctx = Context()
    Main().subcommands(Serpro(ctx), Dataprev(ctx)).main(args)
    println(ctx.dtpFiles)
    println(ctx.serproFiles)

    val dtpList = ctx.dtpFiles
    val dtpDF = when (dtpList.size) {
        1 -> {
            val p = DtpPDFReader()
            val filepath = dtpList.first()
            when (filepath.substringAfterLast(".").lowercase()) {
                "csv", "txt" -> DtpDataFrame.fromCsvFile(filepath)
                "pdf" -> DtpDataFrame.fromText(p.read(filepath))
                else -> throw IllegalArgumentException("Somente arquivos csv, txt ou pdf são permitidos")
            }
        }

        else -> throw NotImplementedError("Não implementado")
    }

    var serproFiles = ctx.serproFiles
    val serprodf = SerproDataframe.fromCsvFile(serproFiles)


    val combined = Dataframes.combine(serprodf.dataframe, dtpDF.dataframe)
        .distinctBy("Nome").sortByDesc(DtpDataFrame.uf, SerproDataframe.notaFinal)

    combined.print(rowsLimit = 60)

}

class Main : CliktCommand(allowMultipleSubcommands = true) {
    override fun run() {
        println("Run")
    }
}


open class Dataprev(val ctx: Context) : CliktCommand("dataprev") {
    private val funcionarios: Set<Path> by option("-f").path(mustExist = true)
        .multiple().unique()

    override fun run() {
        ctx.dtpFiles.addAll(this.funcionarios.map { it.pathString })
    }
}

class Serpro(val ctx: Context) : CliktCommand("serpro") {
    private val candidatos: Set<Path> by argument().path(mustExist = true)
        .multiple()
        .unique()

    override fun run() {
        ctx.serproFiles.addAll(candidatos.map { it.pathString })
    }

}