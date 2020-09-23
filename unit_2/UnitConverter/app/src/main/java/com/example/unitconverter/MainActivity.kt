package com.example.unitconverter

import android.os.Bundle
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

        binding.calculateVolumeButton.setOnClickListener { calculateVolume() }
        binding.calculateWeightButton.setOnClickListener { calculateWeight() }
        binding.calculateTemperatureButton.setOnClickListener { calculateTemperature() }

        binding.spinnerVolumeTo.setSelection(1)
        binding.spinnerWeightTo.setSelection(1)
        binding.spinnerTemperatureTo.setSelection(1)
    }

    private fun calculateVolume() {
        val volume = getDoubleFromTextField(binding.volumeToConvert)
        if (volume == null || volume == 0.0) {
            binding.volumeConversionResult.text = "0.0"
            return
        } else if (sameUnits(binding.spinnerVolumeFrom, binding.spinnerVolumeTo)) {
            binding.volumeConversionResult.text = binding.volumeToConvert.text
            return
        }

        val from = getVolumeUnit(binding.spinnerVolumeFrom.selectedItemPosition)
        val to = getVolumeUnit(binding.spinnerVolumeTo.selectedItemPosition)
        binding.volumeConversionResult.text = String.format("%.2f", volume.convertVolume(from, to))
    }

    private fun calculateWeight() {
        val weight = getDoubleFromTextField(binding.weightToConvert)
        if (weight == null || weight == 0.0) {
            binding.weightConversionResult.text = "0.0"
            return
        } else if (sameUnits(binding.spinnerWeightFrom, binding.spinnerWeightTo)) {
            binding.weightConversionResult.text = binding.weightToConvert.text
            return
        }

        val from = getWeightUnit(binding.spinnerWeightFrom.selectedItemPosition)
        val to = getWeightUnit(binding.spinnerWeightTo.selectedItemPosition)
        binding.weightConversionResult.text = String.format("%.2f", weight.convertWeight(from, to))
    }

    private fun calculateTemperature() {
        val temperature = getDoubleFromTextField(binding.temperatureToConvert)

        if (temperature == null || temperature == 0.0) {
            binding.temperatureConversionResult.text = "0.0"
            return
        } else if (sameUnits(binding.spinnerTemperatureFrom, binding.spinnerTemperatureTo)) {
            binding.temperatureConversionResult.text = binding.temperatureToConvert.text
            return
        }

        val from = getTemperatureUnit(binding.spinnerTemperatureFrom.selectedItemPosition)
        binding.temperatureConversionResult.text =
            String.format("%.2f", temperature.convertTemperature(from))
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
}