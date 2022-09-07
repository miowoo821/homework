package com.leander.homework

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


/* Created on 2022/9/8 */

class DetailActivity: AppCompatActivity() {
    lateinit var tDetail: TextView

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        tDetail = findViewById(R.id.tDetail)
        val text : String? = intent.getStringExtra("text")
        tDetail.text = text
    }
}