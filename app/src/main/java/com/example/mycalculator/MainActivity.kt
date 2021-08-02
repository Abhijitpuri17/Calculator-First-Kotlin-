package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.math.RoundingMode

class MainActivity : AppCompatActivity()
    {
            override fun onCreate(savedInstanceState: Bundle?)
            {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)
            }


            fun onDigitClick(view : View)
            {
                  tvResult.append((view as Button).text)
            }

            fun onDecimalClick(view : View)
            {

                val exp = tvResult.text.toString()

                var indexOfOperator: Int? = null


                // - at first place implies num is negative
                 if (exp.contains('-') && !exp.startsWith('-')) indexOfOperator =
                    exp.indexOf('-')

                 else if (exp.contains('+')) indexOfOperator = exp.indexOf('+')
                else if (exp.contains('x')) indexOfOperator = exp.indexOf('x')
                else if (exp.contains('÷')) indexOfOperator = exp.indexOf('÷')

                // if there are two numbers and second num has no dot , append dot and return
                if (indexOfOperator != null) {
                    val indexOfDot = exp.lastIndexOf('.')
                    if (indexOfOperator > indexOfDot) {
                        onDigitClick(view)
                        return
                    }
                }

                 // if first number and exp contains no dot and is not empty
                if (exp != "" && !exp.contains('.'))
                onDigitClick(view)
            }

            fun onOperatorClick(view : View)
            {
                var exp = tvResult.text.toString()
                if ( (exp.contains('-') && exp.lastIndexOf('-') != 0) || exp.contains('+') || exp.contains('-') || exp.contains('x') || exp.contains('÷') ) {
                    onEqualClick(view)
                }
                exp = tvResult.text.toString()
                if ((exp.contains('-') && exp.lastIndexOf('-') == 0) || (!(exp.contains('-')  || exp.contains('+') ||
                            exp.contains('-') || exp.contains('x') || exp.contains('÷'))))
                onDigitClick(view)
            }


            fun onEqualClick(view : View)
            {
                val exp = tvResult.text.toString()

                // Expression must end with a number
                if (exp[exp.length-1] < '0' || exp[exp.length-1] > '9') {
                    Toast.makeText(this, "Invalid Expression", Toast.LENGTH_SHORT).show()
                    return
                }

                try {
                    when {
                        exp.contains('-') && exp.lastIndexOf('-') != 0 -> subtract(exp)
                        exp.contains('+') -> add(exp)
                        exp.contains('x') -> multiply(exp)
                        exp.contains('÷') -> divide(exp)
                    }
                } catch (e : Exception)
                {
                    Toast.makeText(this , e.message , Toast.LENGTH_SHORT).show()
                }


            }

            private fun subtract(exp : String)
            {
                val splitExp = exp.split('-')
                if (splitExp[0] == "" && exp[0] == '-') return
                val ans = (splitExp[splitExp.size - 2].toBigDecimal() - splitExp[splitExp.size-1].toBigDecimal()).stripTrailingZeros().toPlainString()
                tvResult.text = ans.toString()
            }

            private fun add(exp : String)
            {
                val splitExp = exp.split('+')
                val ans = (splitExp[splitExp.size - 2].toBigDecimal() + splitExp[splitExp.size-1].toBigDecimal()).stripTrailingZeros().toPlainString()
                tvResult.text = ans.toString()
            }

            private fun multiply(exp : String)
            {
                val splitExp = exp.split('x')
                var ans = (splitExp[splitExp.size - 2].toBigDecimal().multiply(splitExp[splitExp.size-1].toBigDecimal())).stripTrailingZeros().toPlainString()
                tvResult.text = ans.toString()
            }

            private fun divide(exp : String)
            {
                val splitExp = exp.split('÷')
                val ans = (splitExp[splitExp.size - 2].toBigDecimal().divide(splitExp[splitExp.size-1].toBigDecimal() , 7 , RoundingMode.HALF_UP)).stripTrailingZeros().toPlainString()
                tvResult.text = ans.toString()
            }

            fun onDelete(view : View)
            {
                if(tvResult.text.toString() != "") {
                    var exp = tvResult.text.toString()
                    exp = exp.substring(0, exp.length - 1)
                    tvResult.text = exp
                }
            }
            fun onClear(view : View)
            {
                tvResult.text = ""
            }

    }