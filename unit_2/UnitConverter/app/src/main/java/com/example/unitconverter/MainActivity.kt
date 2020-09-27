package com.example.unitconverter

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.unitconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateVolumeButton.setOnClickListener {
            calculateVolume()
            hideKeyboard(it)
        }
        binding.calculateWeightButton.setOnClickListener {
            calculateWeight()
            hideKeyboard(it)
        }
        binding.calculateTemperatureButton.setOnClickListener {
            calculateTemperature()
            hideKeyboard(it)
        }

        binding.volumeToConvertEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.weightToConvertEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.temperatureToConvertEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }

        binding.spinnerVolumeTo.setSelection(1)
        binding.spinnerWeightTo.setSelection(1)
        binding.spinnerTemperatureTo.setSelection(1)
    }

    private fun calculateVolume() {
        val volume = getDoubleFromTextField(binding.volumeToConvertEditText)
        if (volume == null || volume == 0.0) {
            binding.volumeConversionResult.text = "0.0"
            return
        } else if (sameUnits(binding.spinnerVolumeFrom, binding.spinnerVolumeTo)) {
            binding.volumeConversionResult.text = binding.volumeToConvertEditText.text
            return
        }

        val from = getVolumeUnit(binding.spinnerVolumeFrom.selectedItemPosition)
        val to = getVolumeUnit(binding.spinnerVolumeTo.selectedItemPosition)
        binding.volumeConversionResult.text =
            getFormattedResult(volume.convertVolume(from, to), binding.spinnerVolumeTo)
    }

    private fun calculateWeight() {
        val weight = getDoubleFromTextField(binding.weightToConvertEditText)
        if (weight == null || weight == 0.0) {
            binding.weightConversionResult.text = "0.0"
            return
        } else if (sameUnits(binding.spinnerWeightFrom, binding.spinnerWeightTo)) {
            binding.weightConversionResult.text = binding.weightToConvertEditText.text
            return
        }

        val from = getWeightUnit(binding.spinnerWeightFrom.selectedItemPosition)
        val to = getWeightUnit(binding.spinnerWeightTo.selectedItemPosition)
        binding.weightConversionResult.text =
            getFormattedResult(weight.convertWeight(from, to), binding.spinnerWeightTo)
    }

    private fun calculateTemperature() {
        val temperature = getDoubleFromTextField(binding.temperatureToConvertEditText)

        if (temperature == null || temperature == 0.0) {
            binding.temperatureConversionResult.text = "0.0"
            return
        } else if (sameUnits(binding.spinnerTemperatureFrom, binding.spinnerTemperatureTo)) {
            binding.temperatureConversionResult.text = binding.temperatureToConvertEditText.text
            return
        }

        val from = getTemperatureUnit(binding.spinnerTemperatureFrom.selectedItemPosition)
        binding.temperatureConversionResult.text =
            getFormattedResult(temperature.convertTemperature(from), binding.spinnerTemperatureTo)
    }

    private fun getDoubleFromTextField(editText: EditText): Double? {
        val stringInTextField = editText.text.toString()
        return stringInTextField.toDoubleOrNull()
    }

    private fun sameUnits(fromSpinner: Spinner, toSpinner: Spinner) =
        fromSpinner.selectedItemId == toSpinner.selectedItemId

    private fun getVolumeUnit(position: Int) = VolumeUnit.values()[position]
    private fun getWeightUnit(position: Int) = WeightUnit.values()[position]
    private fun getTemperatureUnit(position: Int) = TemperatureUnit.values()[position]

    private fun getFormattedResult(result: Double, toSpinner: Spinner): String {
        val formattedResult = String.format("%.2f", result)
        val unit = toSpinner.selectedItem.toString()
        return getString(R.string.conversion_result, formattedResult, unit)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            hideKeyboard(view)
            return true
        }
        return false
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}