package com.example.viacep

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

object ExportUtils {
    fun exportToCsv(context: Context, data: List<String>) {
        val fileName = "addresses.csv"
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)

        try {
            // Criar o arquivo e escrever os dados
            FileOutputStream(file).use { outputStream ->
                OutputStreamWriter(outputStream).use { writer ->
                    writer.write("CEP,Street,Neighborhood,City\n")
                    data.forEach { writer.write("$it\n") }
                }
            }

            // Log do caminho do arquivo para depuração
            Log.d("ExportUtils", "CSV salvo em: ${file.absolutePath}")

            // Compartilhar o arquivo diretamente após a criação
            shareCsv(context, file)
        } catch (e: Exception) {
            Log.e("ExportUtils", "Erro ao exportar CSV: ${e.message}", e)
        }
    }

    fun shareCsv(context: Context, file: File) {
        try {
            // Obter URI segura com FileProvider
            val uri: Uri = FileProvider.getUriForFile(
                context,
                "com.example.viacep.fileprovider", // Deve ser o mesmo que o authorities no AndroidManifest.xml
                file
            )

            // Intent para compartilhar o arquivo
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(shareIntent, "Compartilhar CSV"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

