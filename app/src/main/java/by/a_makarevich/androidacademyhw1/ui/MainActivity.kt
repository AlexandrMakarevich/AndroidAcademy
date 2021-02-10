package by.a_makarevich.androidacademyhw1.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import by.a_makarevich.androidacademyhw1.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d ("MyLog", "MainActivity_onCreate")
    }
}