package com.example.pizza.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.pizza.PizzaSize
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {

    private val _size: MutableLiveData<PizzaSize> = MutableLiveData()
    val size: LiveData<PizzaSize> get() = _size

    private val _topping: MutableLiveData<String> = MutableLiveData()
    val topping: LiveData<String> get() = _topping

    private val _date: MutableLiveData<String> = MutableLiveData()
    val date: LiveData<String> get() = _date

    private val _price: MutableLiveData<Double> = MutableLiveData()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    val dateOptions = getPickupOptions()

    init {
        resetOrder()
    }

    fun setSize(size: PizzaSize) {
        _size.value = size
        updatePrice()
    }

    fun setTopping(desiredTopping: String) {
        _topping.value = desiredTopping
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    fun hasNoToppingSet(): Boolean {
        return _topping.value.isNullOrEmpty()
    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        // Create a list of dates starting with the current date and the following 3 dates
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    private fun updatePrice() {
        var calculatedPrice = getPricePerSize(_size.value ?: PizzaSize.Small)
        // If the user selected the first option (today) for pickup, add the surcharge
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

    fun resetOrder() {
        _size.value = PizzaSize.Small
        _topping.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    private fun getPricePerSize(size: PizzaSize): Double =
        when (size) {
            PizzaSize.Small -> 12.0
            PizzaSize.Medium -> 14.0
            PizzaSize.Large -> 16.0
        }
}