package com.nicolascristaldo.imcapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nicolascristaldo.imcapp.MainActivity.Companion.RESULT_KEY

class ResultActivity : AppCompatActivity() {
    private lateinit var tvImc: TextView
    private lateinit var tvResult: TextView
    private lateinit var tvDescription: TextView

    private lateinit var btn_recalculate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val result: Double = intent.extras?.getDouble(RESULT_KEY) ?: -1.0

        initComponents()
        initUI(result)
        initListeners()
    }

    private fun initListeners() {
        btn_recalculate.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initUI(result: Double) {
        tvImc.text = result.toString()
        when(result) {
            in 0.0..18.50 -> {
                tvResult.text = getString(R.string.result_underweight)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.color_underweight))
                tvDescription.text = getString(R.string.description_underweight)
            }
            in 18.51..24.99 -> {
                tvResult.text = getString(R.string.result_normal)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.color_normal))
                tvDescription.text = getString(R.string.description_normal)
            }
            in 25.0..29.99 -> {
                tvResult.text = getString(R.string.result_overweight)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.color_overweight))
                tvDescription.text = getString(R.string.description_overweight)
            }
            in 30.0..99.99 -> {
                tvResult.text = getString(R.string.result_obesity)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.color_obesity))
                tvDescription.text = getString(R.string.description_obesity)
            }
            else -> {
                tvResult.text = getString(R.string.error)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.color_obesity))
                tvImc.text = getString(R.string.error)
                tvDescription.text = getString(R.string.error)
            }
        }
    }

    private fun initComponents() {
        tvResult = findViewById(R.id.tvResult)
        tvImc = findViewById(R.id.tvImc)
        tvDescription = findViewById(R.id.tvDescription)
        btn_recalculate = findViewById(R.id.btnRecalculate)
    }
}