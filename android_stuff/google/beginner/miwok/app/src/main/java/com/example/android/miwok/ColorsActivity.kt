package com.example.android.miwok

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView

import java.util.ArrayList
import java.util.Arrays

class ColorsActivity : AppCompatActivity() {
    internal val TAG = "ColorsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list)
        // Set the titlebar color
        val colorDrawable = ContextCompat.getDrawable(applicationContext, R.color.category_colors)
        supportActionBar!!.setBackgroundDrawable(colorDrawable)

        val words = arrayOf(Word("red", "weṭeṭṭi", R.drawable.color_red), Word("green", "chokokki", R.drawable.color_green), Word("brown", "ṭakaakki", R.drawable.color_brown), Word("gray", "ṭopoppi", R.drawable.color_gray), Word("black", "kululli", R.drawable.color_black), Word("white", "kelelli", R.drawable.color_white), Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow), Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow))
        val wordsList = ArrayList(Arrays.asList(*words))
        val itemsAdapter = WordAdapter(this, wordsList, R.color.category_colors)
        val listView = findViewById(R.id.list) as ListView?
        listView!!.adapter = itemsAdapter
    }
}
