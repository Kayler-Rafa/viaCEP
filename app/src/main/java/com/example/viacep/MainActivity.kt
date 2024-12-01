package com.example.viacep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = DatabaseHelper(this)

        setContent {
            var cep by remember { mutableStateOf(TextFieldValue("")) }
            var result by remember { mutableStateOf("") }
            val allResults = remember { mutableStateListOf<String>() }
            val coroutineScope = rememberCoroutineScope()

            // Carregar os dados do banco ao iniciar
            LaunchedEffect(Unit) {
                allResults.addAll(dbHelper.getAllAddresses())
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Campo de entrada
                BasicTextField(
                    value = cep,
                    onValueChange = { cep = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(8.dp)
                )

                // Botão de pesquisa
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                val address = ViaCepService.getAddress(cep.text)
                                if (address != null && address.cep != null) {
                                    dbHelper.insertAddress(
                                        address.cep,
                                        address.logradouro ?: "N/A",
                                        address.bairro ?: "N/A",
                                        address.localidade ?: "N/A"
                                    )
                                    val displayResult =
                                        "CEP: ${address.cep}, Rua: ${address.logradouro}, Bairro: ${address.bairro}, Cidade: ${address.localidade}"
                                    result = displayResult
                                    allResults.add(displayResult)
                                } else {
                                    result = "CEP não encontrado!"
                                }
                            } catch (e: Exception) {
                                result = "Erro ao buscar o CEP."
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                ) {
                    Text("Pesquisar", color = Color.White)
                }

                // Botão para apagar histórico
                Button(
                    onClick = {
                        dbHelper.clearAll()
                        allResults.clear()
                        result = ""
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                ) {
                    Text("Apagar Histórico", color = Color.White)
                }

                // Exibição de resultados
                Text(result, color = Color.White)
                allResults.forEach {
                    Text(it, color = Color.Gray)
                }

                // Botão de exportar CSV
                Button(
                    onClick = {
                        ExportUtils.exportToCsv(this@MainActivity, dbHelper.getAllAddresses())
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                ) {
                    Text("Exportar CSV", color = Color.White)
                }

                // Créditos
                Text(
                    "Powered by ViaCEP API",
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}