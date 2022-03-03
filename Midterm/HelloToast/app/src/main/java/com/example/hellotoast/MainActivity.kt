package com.example.hellotoast

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.widget.Toast
import com.example.hellotoast.databinding.ActivityMainBinding
import android.content.Intent
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    var EXTRA_MESSAGE = "com.example.android.hellotoast.extra.MESSAGE"
    private var mCount = 0
    private lateinit var mShowCount: TextView
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        mShowCount = binding.showCount
        binding.buttonCount.setOnClickListener { countUp() }
        binding.buttonToast.setOnClickListener { showToast() }
        mShowCount.text = viewModel.mCount.toString()
        val view = binding.root
        setContentView(binding.root)
    }

    private fun showToast() {
        val toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT)
        toast.show()
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("MESSAGE", mShowCount.text.toString())
        startActivity(intent)
    }

    private fun countUp() {
        viewModel.countup()
        mShowCount.text = viewModel.mCount.toString()
    }
}