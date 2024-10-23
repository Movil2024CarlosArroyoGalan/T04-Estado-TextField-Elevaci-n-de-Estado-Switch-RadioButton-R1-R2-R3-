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
    //Declaracion de variables de los numeros
    var num1 by rememberSaveable { mutableStateOf("") }
    var num2 by rememberSaveable { mutableStateOf("") }
    //Variable de seleccion por defecto
    var selectedOperation by rememberSaveable { mutableStateOf("SUMA") }
    //Radiobutton para seleccionar las diferentes operaciones
    val radioOptions = stringArrayResource(R.array.operaciones).toList()
    //Llamada al metodo creado para hacer los calculos pasandole las variables anteriores a usar
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
        //Espacios entre cada objeto para que no estén pegados unos de otros
        Spacer(modifier = Modifier.height(16.dp))
        //Cuadro de texto en el que se ingresará el valor a sumar
        OutlinedTextField(
            value = num1,
            onValueChange = {num1 = it},
            label = { Text(stringResource(R.string.n_mero_1)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        //Espacios entre cada objeto para que no estén pegados unos de otros
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = num2,
            onValueChange = {num2 = it},
            label = { Text(stringResource(R.string.n_mero_2)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        //Espacios entre cada objeto para que no estén pegados unos de otros
        Spacer(modifier = Modifier.height(16.dp))

        // Opciones del radiobutton
        RadioButton(
            listaOpciones = radioOptions,
            operacionSeleccionada = selectedOperation,
            onOptionSelected = { selectedOperation = it }
        )
        //Espacios entre cada objeto para que no estén pegados unos de otros
        Spacer(modifier = Modifier.height(16.dp))
        //Muestra el resultado
        Text(text = "Resultado: $resultado", fontSize = 32.sp)
        //Espacios entre cada objeto para que no estén pegados unos de otros
        Spacer(modifier = Modifier.height(16.dp))
        //Los diferentes iconos que se van a mostrar en caso de que selecciones una especifica
        val icon = when (selectedOperation) {
            "SUMA" -> Icons.Default.Add
            "RESTA" -> Icons.Default.Menu
            "MULT" -> Icons.Default.Clear
            else -> Icons.Default.Edit
        }
        //El icono que será mostrado y que recibirá las imagenes al haber recibido el objecto
        Icon(imageVector = icon, contentDescription = stringResource(R.string.operaci_n_seleccionada), modifier = Modifier.size(100.dp))
    }
}
//Configuracion del radiobutton
@Composable
fun RadioButton(
    //Atributos y operaciones de estos
    listaOpciones: List<String>,
    operacionSeleccionada: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    //Lugar en el que esté se contendrá
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        //Configuracion que nos permitirá que por cada elemento de la lista de opciones, al ser clicadoo el radio button poder mostrar las imagenes
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