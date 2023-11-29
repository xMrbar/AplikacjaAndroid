package com.example.aplikacjaandroid.ui
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.Counter
import com.example.aplikacjaandroid.FileManager
import com.example.aplikacjaandroid.R
import com.example.aplikacjaandroid.selectfield.SelectField
import com.example.aplikacjaandroid.textinput.TextInput
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal


class AddNewOutPlanViewModel(private val context: Context): ViewModel() {
    val selectedOption1 = MutableStateFlow("Częstość płatności")
    val selectedOption2 = MutableStateFlow("Kategoria")
    val tytul = MutableStateFlow("")
    val kwota = MutableStateFlow(BigDecimal("0.00"))
    private val fileManager = FileManager("accountBalance.txt")

    @Composable
    fun dateSelect()
    {

    }

    @Composable
    fun categorySelect()
    {
        val options = listOf("CAR", "ELECTRICITY", "TRAVEL", "HOME", "CLOTHES", "TV")
        mySelectBox(options = options, selectedOption2)
    }

    @Composable
    fun textGet(title: String)
    {
        InputText(title = title, tytul = tytul)
    }

    @Composable
    fun amountGet(title:String)
    {
        InputCount(title, kwota)
    }

    fun appendToFile(tytul1: String, selectCzestoscPlatnosci: String, kwota1: BigDecimal, kategoria: String):Boolean
    {
        if (tytul1.isEmpty() || selectCzestoscPlatnosci.equals("Częstość płatności") ||
            kategoria.equals("Kategoria") || kwota1.toDouble() <= 0)
        {
            return false
        }
        appender(tytul1, selectCzestoscPlatnosci, kwota1, kategoria)
        return true
    }

    private fun appender(tytul1: String, selectCzestoscPlatnosci: String, kwota1: BigDecimal, kategoria: String)
    {
        fileManager.appendToFile(tytul1 + ";" + selectCzestoscPlatnosci + ";" + kwota1 + ";" + kategoria, context)
    }
}
