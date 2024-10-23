package net.iessochoa.carlosarroyogalan.ejercicio2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import net.iessochoa.carlosarroyogalan.ejercicio2.ui.theme.Ejercicio2Theme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ejercicio2Theme {
                //Función llamada
                TipTimeLayout()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ejercicio2Theme {
        TipTimeLayout()
    }
}

@Composable
fun TipTimeLayout() {
    //Gestion de la propina en caso de redondeo
    var roundUp by remember { mutableStateOf(false) }
    //Gestion de la entrada y la cantidad de dinero
    var tipInput by remember { mutableStateOf("") }
    var amountInput by remember { mutableStateOf("") }
    //Cantidad y porcentaje convertidos a valores
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    //Calculo de la propina con la funcion diseñada
    val tip = calculateTip(amount, tipPercent, roundUp)
    //Organizacion de la columna y sus elementos
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Texto "Calculate Tip" ubicado
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        //Entrada de la factura a pagar
        EditNumberField(
            label = R.string.bill_amount,
            leadingIcon = R.drawable.baseline_attach_money_24,
            //Configuraciones del teclado
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = amountInput,
            onValueChange = { amountInput = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        //Entrada de el porcentaje a aplicar en la factura
        EditNumberField(
            label = R.string.how_was_the_service,
            leadingIcon = R.drawable.baseline_percent_24,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = tipInput,
            onValueChange = {tipInput = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        //Interruptor en caso de querer redondear la propina
        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = {roundUp = it},
            modifier = Modifier.padding(bottom = 32.dp)
        )
        //Propina calculada
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}
//Calculos para calcular la propina
private fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
    var tip = tipPercent / 100 * amount
    //En caso de querer redondear redondea hacia arriba con este if
    if (roundUp){
        tip = kotlin.math.ceil(tip)
    }
    //Devuelve la propina
    return NumberFormat.getCurrencyInstance().format(tip)
}

//Entrada de los elementos
@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    //Etiqueta del campo
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    //Icono
    @DrawableRes leadingIcon: Int
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
        modifier = modifier
    )
}
//Fila con el interrumptor en caso de querer redondear
@Composable
fun RoundTheTipRow(
    modifier: Modifier = Modifier,
    roundUp: Boolean, //Estado del interruptor
    onRoundUpChanged: (Boolean) -> Unit //Funcion en caso de cambio
    ) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Texto preguntando el redondeo
        Text(text = stringResource(R.string.round_up_tip))
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            //Estado
            checked = roundUp,
            //Funcion de cambio
            onCheckedChange = onRoundUpChanged,
        )
    }
}