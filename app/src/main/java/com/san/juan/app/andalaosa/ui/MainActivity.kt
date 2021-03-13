package com.san.juan.app.andalaosa.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.san.juan.app.andalaosa.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}