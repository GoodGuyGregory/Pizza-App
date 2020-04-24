package com.teamtreehouse.pizzakeeper

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.teamtreehouse.pizzakeeper.data.Pizza

class MainViewModel: ViewModel() {
//    maintain a list of Pizzas based on the data in the DB
//    This can be done with Live Data
private var pizzas: LiveData<List<Pizza>>? = null
    fun getPizzas(): LiveData<List<Pizza>> {
        if (pizzas == null) {
            pizzas = db.PizzaDao().getAll()
        }
        return pizzas!!
    }

}