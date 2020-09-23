package com.example.unitconverter

import com.example.unitconverter.VolumeUnit.*
import com.example.unitconverter.WeightUnit.*

data class VolumeConversion(val units: Pair<VolumeUnit, VolumeUnit>, val multiplier: Double)
data class WeightConversion(val units: Pair<WeightUnit, WeightUnit>, val multiplier: Double)

private val volumeConversions = mutableListOf(
    VolumeConversion(Pair(MILLILITER, TEASPOON), 0.202884),
    VolumeConversion(Pair(MILLILITER, TABLESPOON), 0.067628),
    VolumeConversion(Pair(MILLILITER, FLUID_OUNCE), 0.033814),
    VolumeConversion(Pair(MILLILITER, CUP), 0.00422675),
    VolumeConversion(Pair(TEASPOON, TABLESPOON), 0.333333),
    VolumeConversion(Pair(TEASPOON, FLUID_OUNCE), 0.166667),
    VolumeConversion(Pair(TEASPOON, CUP), 0.0208333),
    VolumeConversion(Pair(TABLESPOON, FLUID_OUNCE), 0.5),
    VolumeConversion(Pair(TABLESPOON, CUP), 0.0625),
    VolumeConversion(Pair(FLUID_OUNCE, CUP), 0.125)
)

private val weightConversions = mutableListOf(
    WeightConversion(Pair(GRAM, OUNCE), 0.035274),
    WeightConversion(Pair(GRAM, POUND), 0.00220462),
    WeightConversion(Pair(OUNCE, POUND), 0.0625)
)

fun Double.convertVolume(from: VolumeUnit, to: VolumeUnit): Double {
    val conversion = volumeConversions.first { it.units.toList().containsAll(listOf(from, to)) }
    return when (from) {
        conversion.units.first -> this * conversion.multiplier
        else -> this / conversion.multiplier
    }
}

fun Double.convertWeight(from: WeightUnit, to: WeightUnit): Double {
    val conversion = weightConversions.first { it.units.toList().containsAll(listOf(from, to)) }
    return when (from) {
        conversion.units.first -> this * conversion.multiplier
        else -> this / conversion.multiplier
    }
}

fun Double.convertTemperature(from: TemperatureUnit) = when (from) {
    TemperatureUnit.FAHRENHEIT -> (this - 32) * 5 / 9
    TemperatureUnit.CELSIUS -> (this * 9 / 5) + 32
}