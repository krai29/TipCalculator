package com.krai29.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.krai29.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode,_ ->
            handleKeyEvent(view, keyCode)
        }
        }


    private fun calculateTip() {
        val serviceCost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()
        if (serviceCost==null||serviceCost==0.0){
            binding.tipResult.text = ""
            return
        }
        val tipPercent = when(binding.tipOptions.checkedRadioButtonId){
            R.id.option_20_percent -> 0.20
            R.id.option_10_percent -> 0.10
            else -> 0.15
        }

        var tip = serviceCost*tipPercent
        if (binding.roundUpSwitch.isChecked){
            tip = ceil(tip)
        }
        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }


    private fun handleKeyEvent(view: View,keyCode:Int): Boolean{
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
            return true
        }
        return false
    }
}