package com.nicolascristaldo.imcapp

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider

class MainActivity : AppCompatActivity() {
    private var currentWeight: Int = 40
    private var currentHeight: Int = 100

    private lateinit var maleCardView: CardView
    private lateinit var femaleCardView: CardView

    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider

    private lateinit var btnSubtractWeight: FloatingActionButton
    private lateinit var btnPlusWeight: FloatingActionButton
    private lateinit var tvWeight: TextView

    private lateinit var btnCalculate: Button

    private var isMaleSelected = true
    private var isFemaleSelected = false

    companion object {
        const val RESULT_KEY = "RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initListeners()
        initUi()
    }

    private fun initComponents() {
        maleCardView = findViewById(R.id.cvMale)
        femaleCardView = findViewById(R.id.cvFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        tvWeight = findViewById(R.id.tvWeight)
        btnPlusWeight = findViewById(R.id.btnPlusWeight)
        btnSubtractWeight = findViewById(R.id.btnSubtractWeight)
        btnCalculate = findViewById(R.id.btnCalculate)
    }

    private fun initListeners() {
        maleCardView.setOnClickListener {
            if (!isMaleSelected) {
                changeGender()
                setGenderColor()
            }
        }

        femaleCardView.setOnClickListener {
            if (!isFemaleSelected) {
                changeGender()
                setGenderColor()
            }
        }

        rsHeight.addOnChangeListener { _, value, _ ->
            currentHeight = value.toInt()
            val df = DecimalFormat("#.## cm")
            val result = df.format(value)
            tvHeight.text = result
        }

        btnPlusWeight.setOnClickListener {
            if (currentWeight < 400) {
                currentWeight += 1
                setWeight()
            }
        }

        btnSubtractWeight.setOnClickListener {
            if (currentWeight > 1) {
                currentWeight -= 1
                setWeight()
            }
        }

        btnCalculate.setOnClickListener {
            val result = calculateIMC()
            navigateToResult(result)
        }
    }

    private fun navigateToResult(result: Double) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(RESULT_KEY, result)
        startActivity(intent)
    }

    private fun calculateIMC(): Double {
        val df = DecimalFormat("#.##")
        val imc = currentWeight / (currentHeight.toDouble() / 100 * currentHeight.toDouble() / 100)
        return df.format(imc).toDouble()
    }

    private fun setWeight() {
        tvWeight.text = currentWeight.toString()
    }

    private fun changeGender() {
        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
    }

    private fun setGenderColor() {
        maleCardView.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        femaleCardView.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
    }

    private fun getBackgroundColor(isSelectedCard: Boolean): Int {

        val colorReference = if (isSelectedCard) {
            R.color.selected_component_background
        } else {
            R.color.unselected_component_background
        }

        return ContextCompat.getColor(this, colorReference)
    }

    private fun initUi() {
        setGenderColor()
        setWeight()
    }
}