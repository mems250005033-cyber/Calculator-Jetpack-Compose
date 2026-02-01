package com.example.calculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {



    private lateinit var display: TextView

    private var firstNumber = 0.0
    private var operator = ""
    private var isNewInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnDot
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener {
                appendNumber((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { setOperator("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { setOperator("/") }

        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculate() }
        findViewById<Button>(R.id.btnC).setOnClickListener { clearLast() }
        findViewById<Button>(R.id.btnAC).setOnClickListener { clearAll() }
    }

    private fun appendNumber(number: String) {
        if (isNewInput) {
            display.text = number
            isNewInput = false
        } else {
            display.append(number)
        }
    }

    private fun setOperator(op: String) {
        val currentText = display.text.toString()
        firstNumber = currentText.toDoubleOrNull() ?: 0.0
        operator = op
        isNewInput = true
    }

    private fun calculate() {
        val secondNumber = display.text.toString().toDoubleOrNull() ?: 0.0

        val result = when (operator) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> {
                if (secondNumber == 0.0) {
                    display.text = "Error"
                    return
                } else firstNumber / secondNumber
            }
            else -> return
        }

        display.text = result.toString()
        isNewInput = true
    }

    private fun clearLast() {
        val text = display.text.toString()
        if (text.isNotEmpty()) {
            display.text = text.dropLast(1)
        }
    }

    private fun clearAll() {
        display.text = "0"
        firstNumber = 0.0
        operator = ""
        isNewInput = true
    }

    private fun performOperation(a: Double, b: Double, op: String): Double {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> if (b != 0.0) a / b else Double.NaN
            "^" -> Math.pow(a, b)
            else -> b
        }
    }
}
