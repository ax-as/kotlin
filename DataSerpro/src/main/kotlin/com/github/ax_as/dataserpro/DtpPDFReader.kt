package com.github.ax_as.dataserpro

import org.apache.pdfbox.pdmodel.PDDocument
import technology.tabula.ObjectExtractor
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm
import java.io.File

class DtpPDFReader {

    fun readAll(filepaths: List<String>): List<String> {
        val list = arrayListOf<String>()
        for (filepath in filepaths) {
            list.add(read(filepath))
        }
        return list
    }

    fun read(filepath: String): String {

        val pdfFile = File(filepath)
        val sb = StringBuilder()
        PDDocument.load(pdfFile).use {
            val sea = SpreadsheetExtractionAlgorithm()
            val pi = ObjectExtractor(it).extract()

            while (pi.hasNext()) {
                val page = pi.next()
                val tables = sea.extract(page)
                for (table in tables) {
                    val rows = table.rows
                    for (cell in rows) {
                        for (content in cell) {
                            val text: String =
                                content.getText().replace("\n", " ")
                                    .replace("\r", " ")
                            sb.append("$text,")
                        }
                        sb.appendLine()
                    }
                }
            }
        }


        return sb.toString()
    }

}
