package com.teamtreehouse.pizzakeeper

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.teamtreehouse.pizzakeeper.data.Pizza
import com.teamtreehouse.pizzakeeper.data.PizzaDatabase
import com.teamtreehouse.pizzakeeper.data.PizzaTopping

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.util.*


@RunWith(AndroidJUnit4::class)
class PizzaTests {

    //    Create a Pizza to Test with:
    val testPizza = Pizza(0, "Hawaiian", Date())
    val testToppingIds = listOf(1, 7)

    val appContext = InstrumentationRegistry.getTargetContext()
    //        Creates an instance of the Pizza Database
    val db = Room.databaseBuilder(appContext, PizzaDatabase::class.java, "PizzaDatabase")
            .build()

    @Test
    fun pizzaTest() {
        db.clearAllTables()
//        insert the test pizza into the table and out again
        db.PizzaDao().insert(testPizza)

        val returnedPizza = db.PizzaDao().getPizzaById(testPizza.id)
        assertEquals(testPizza, returnedPizza)

    }

    @Test
    fun pizzaToppingTest(){
//        Arrange
        db.clearAllTables()
        db.PizzaDao().insert(testPizza)
        toppings.forEach{
            db.ToppingDao().insert(it)
        }

//      Act: insert and retrieve data
        testToppingIds.forEach{
            val pizzaTopping = PizzaTopping(testPizza.id, it)
            db.PizzaToppingDao().insert(pizzaTopping)
        }

        val returnedToppingIds = db.PizzaToppingDao().getToppingIdsForPizzaID(testPizza.id)
//        Assert
        assertEquals(testToppingIds, returnedToppingIds)


    }

}
