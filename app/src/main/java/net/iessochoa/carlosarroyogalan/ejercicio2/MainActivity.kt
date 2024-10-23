package net.iessochoa.carlosarroyogalan.ejercicio2

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import net.iessochoa.carlosarroyogalan.ejercicio2.ui.theme.Ejercicio2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ejercicio2Theme {
                CalculadoraApp()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ejercicio2Theme {
        CalculadoraApp()
    }
}

@Composable
fun CalculadoraApp() {
    var num1 by rememberSaveable { mutableStateOf("") }
    var num2 by rememberSaveable { mutableStateOf("") }
    var selectedOperation by rememberSaveable { mutableStateOf("SUMA") }
    val radioOptions = stringArrayResource(R.array.operaciones).toList() // Opciones para las operaciones
    val resultado = CalcularResultado(num1, num2, selectedOperation)
    // Diseño básico con campos de texto y espaciadores
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.calculadora), style = MaterialTheme.typography.displaySmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = num1,
            onValueChange = {num1 = it},
            label = { Text(stringResource(R.string.n_mero_1)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = num2,
            onValueChange = {num2 = it},
            label = { Text(stringResource(R.string.n_mero_2)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Opciones del radiobutton
        RadioButton(
            listaOpciones = radioOptions,
            operacionSeleccionada = selectedOperation,
            onOptionSelected = { selectedOperation = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Resultado: $resultado", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(16.dp))
        //Iconos
        val icon = when (selectedOperation) {
            "SUMA" -> Icons.Default.Add
            "RESTA" -> Icons.Default.Menu
            "MULT" -> Icons.Default.Clear
            else -> Icons.Default.Edit
        }

        Icon(imageVector = icon, contentDescription = "Operación seleccionada", modifier = Modifier.size(100.dp))
    }
}
//Configuracion del radiobutton
@Composable
fun RadioButton(
    //Opciones de Radiobutton
    listaOpciones: List<String>,
    operacionSeleccionada: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        //Por cada opcion de radioButton
        listaOpciones.forEach { opcion ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = operacionSeleccionada == opcion,
                    onClick = { onOptionSelected(opcion) }
                )
                Text(text = opcion)
            }
        }
    }
}

// Función para calcular el resultado
@Composable
fun CalcularResultado(num1: String, num2: String, operation: String): String {
    //Variables declaradas
    val number1 = num1.toDoubleOrNull() ?: 0.0
    val number2 = num2.toDoubleOrNull() ?: 0.0
    return when (operation) {
        //Configuracion de las diferentes operaciones
        "SUMA" -> (number1 + number2).toString()
        "RESTA" -> (number1 - number2).toString()
        "MULT" -> (number1 * number2).toString()
        "DIV" -> if (number2 != 0.0) (number1 / number2).toString() else "0"
        else -> "0"
    }
}