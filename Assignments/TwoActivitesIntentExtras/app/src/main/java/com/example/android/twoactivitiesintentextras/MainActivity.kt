package com.example.android.twoactivitiesintentextras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

const val EXTRA_MESSAGE = "com.example.android.twoactivitiesintentextras.extra.MESSAGE"
const val EXTRA_TITLE = "com.example.android.twoactivitiesintentextras.extra.MESSAGE"


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.box_one).setOnClickListener {
            changeViewOnSecondActivity(it)
        }
        findViewById<Button>(R.id.box_two).setOnClickListener {
            changeViewOnSecondActivity(it)
        }
        findViewById<Button>(R.id.box_three).setOnClickListener {
            changeViewOnSecondActivity(it)
        }
    }

    private fun changeViewOnSecondActivity(view: View) {

        val scrollText = when (view.id) {
            R.id.box_one -> getString(R.string.article1)
            R.id.box_two -> getString(R.string.article2)
            R.id.box_three -> getString(R.string.article3)
            else -> getString(R.string.default_text)
        }
        //val message = scrollText.toString()

        val intent = Intent(this, ScrollingTextView::class.java).apply {
            putExtra(EXTRA_MESSAGE, scrollText)
        }
        startActivity(intent)
    }

}
