package com.teamtreehouse.pizzakeeper

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import com.teamtreehouse.pizzakeeper.data.Pizza
import com.teamtreehouse.pizzakeeper.data.PizzaTopping
import java.util.*
import kotlin.concurrent.thread

class CreatorActivity : AppCompatActivity() {
    private var pizzaId = -1
//    Lateinit
    lateinit var pizzaView: PizzaView
//    Creates New View Model
    lateinit var viewModel: CreatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creator)

        pizzaId = intent.getIntExtra(PIZZA_ID, -1)
//        onCreate for importing the new ViewModel oncreate
        viewModel = ViewModelProviders.of(this).get(CreatorViewModel::class.java)
        thread {
            if (pizzaId !== -1) {
                viewModel.pizzaName = db.PizzaDao().getPizzaById(pizzaId).name
                val toppingIds = db.PizzaToppingDao().getToppingIdsForPizzaID(pizzaId)
                toppingIds.forEach {
                    val topping = db.ToppingDao().getToppingById(it)
                    viewModel.switchStates[topping] = true
                }
            }
            title = viewModel.pizzaName
        }

        pizzaView = PizzaView(this, viewModel.switchStates)

        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)
        frameLayout.addView(pizzaView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val adapter = CreatorAdapter(pizzaView, viewModel)
        val recyclerView = findViewById<RecyclerView>(R.id.toppingsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        adapter.list.addAll(toppings)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.creator_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
//                Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show()
//                finish()
                thread {
                    if (pizzaId != -1) {
                        db.PizzaToppingDao().deletePizzaById(pizzaId)
                        db.PizzaDao().deletePizzaById(pizzaId)
                    }
                }
            }
            R.id.edit_name -> {
                val editText = EditText(this)
                editText.setSingleLine()
                AlertDialog.Builder(this).setView(editText).setPositiveButton("Ok", { dialogInterface, i ->
                    if (editText.text.isNotEmpty()) {
                        val text = editText.text.toString()
                        title = text
                        viewModel.pizzaName = text
                    }
                }).show()
            }
            R.id.save -> {
//                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
//                finish()
                thread {
                    if (pizzaId != -1) {
                        db.PizzaToppingDao().deletePizzaById(pizzaId)
                        db.PizzaDao().deletePizzaById(pizzaId)
                    }

                    val pizza = Pizza(null, viewModel.pizzaName, Date())
//                    insert the pizza into the db
                    val newPizzaId = db.PizzaDao().insert(pizza).toInt()

                    viewModel.switchStates.forEach {
                        if (it.value) {
                            val pizzatopping = PizzaTopping(newPizzaId, it.key.id)
                            db.PizzaToppingDao().insert(pizzatopping)
                        }
                    }
                }
            }
        }
        return true
    }
}