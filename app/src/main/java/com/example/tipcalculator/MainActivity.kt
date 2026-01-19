package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Slider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipCalculatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorScreen(modifier: Modifier = Modifier) {

    val orderAmount = remember { mutableStateOf("") }
    val dishesCount = remember { mutableStateOf("") }
    val tipPercentage = remember { mutableStateOf(0f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Заголовок
        Text(
            text = "Калькулятор чаевых",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Поле 1: Сумма заказа
        InputRow(
            label = "Сумма заказа:",
            value = orderAmount.value,
            onValueChange = { orderAmount.value = it },
            placeholder = "Введите сумму"
        )

        // Поле 2: Количество блюд
        InputRow(
            label = "Количество блюд:",
            value = dishesCount.value,
            onValueChange = { dishesCount.value = it },
            placeholder = "Введите количество"
        )
        TipSlider(
            sliderPosition = tipPercentage.value,
            onPositionChange = { tipPercentage.value = it }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(0.4f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Используем BasicTextField вместо TextField
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(0.6f)
                .background(
                    color = Color(0xFFE75480),  // Розовый фон
                    shape = RoundedCornerShape(4.dp)  // Закругленные углы
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFFE75480),  // Розовая рамка
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 12.dp, vertical = 16.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            singleLine = true,
            decorationBox = { innerTextField ->
                // Показываем placeholder если текст пустой
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
fun TipSlider(sliderPosition: Float, onPositionChange: (Float) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Заголовок
        Text(
            text = "Чаевые: ${sliderPosition.toInt()}%",
            style = MaterialTheme.typography.bodyLarge
        )

        // Подписи 0 и 25
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "0")
            Text(text = "25")
        }

        // Сам слайдер (как в лабораторной, но диапазон 0-25)
        Slider(
            modifier = Modifier.padding(horizontal = 10.dp),
            valueRange = 0f..25f,  // Диапазон для чаевых
            value = sliderPosition,
            onValueChange = onPositionChange
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TipCalculatorPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}