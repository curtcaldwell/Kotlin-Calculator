package com.curtcaldwell.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import java.lang.NumberFormatException
import kotlinx.android.synthetic.main.activity_main.*

private const val STATE_PENDING_OPERATION =  "PendingOperation"
private const val STATE_OPERAND1 =  "OPERAND1"
private const val STATE_OPERAND1_STORED = "Operand1_Stored"



class MainActivity : AppCompatActivity() {

    //Variables to hold the operands and type of calculation

    private var operand1: Double? = null
    private var pendingOperation = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clickListener = View.OnClickListener { v ->
            val b = v as Button
            new_number.append(b.text)
        }

        buttonDot.setOnClickListener(clickListener)
        button1.setOnClickListener(clickListener)
        button2.setOnClickListener(clickListener)
        button3.setOnClickListener(clickListener)
        button4.setOnClickListener(clickListener)
        button5.setOnClickListener(clickListener)
        button6.setOnClickListener(clickListener)
        button7.setOnClickListener(clickListener)
        button8.setOnClickListener(clickListener)
        button9.setOnClickListener(clickListener)
        button0.setOnClickListener(clickListener)



        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = new_number.text.toString().toDouble()
                performOperation(value, op)

            } catch (e: NumberFormatException) {
                new_number.setText("")
            }

            pendingOperation = op
            operation.text = pendingOperation
        }



        buttonEquals.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonAddition.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)

        buttonNeg.setOnClickListener{ v ->
            val value = new_number.text.toString()
            if(value.isEmpty() ){
                new_number.setText("-")
            } else{
                try {
                    var doubleValue = value.toDouble()
                    doubleValue *= -1
                    new_number.setText(doubleValue.toString())

                } catch(e: NumberFormatException){
                    new_number.setText("-")
                }
            }


        }

    }

    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {

                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.00) {
                    Double.NaN
                } else {
                    operand1!! / value
                }

                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value

            }
        }
        result.setText(operand1.toString())
        new_number.setText("")


    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(operand1 != null){
            outState.putDouble(STATE_OPERAND1, operand1!!)
            outState.putBoolean(STATE_OPERAND1_STORED, true)
        }
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if(savedInstanceState.getBoolean(STATE_OPERAND1_STORED, false)){
            savedInstanceState.getDouble(STATE_OPERAND1)
        } else {
            null
        }
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1)
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION)

        operation.text = pendingOperation
    }



}
