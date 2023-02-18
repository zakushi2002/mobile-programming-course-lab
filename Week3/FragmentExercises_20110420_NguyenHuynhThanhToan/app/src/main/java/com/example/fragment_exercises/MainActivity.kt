package com.example.fragment_exercises

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = FirstFragment();
        val secondFragment = SecondFragment();

        // Fragment Layout đầu tiên là firstFragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, firstFragment)
            commit()
        }
        val btn1 = findViewById<Button>(R.id.btnFragment1)
        val btn2 = findViewById<Button>(R.id.btnFragment2)
        btn1.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                // Thay đổi fragment
                replace(R.id.flFragment, firstFragment)
                // Thêm vào stack
                addToBackStack(null)
                commit()
            }
        }
        btn2.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                // Thay đổi fragment
                replace(R.id.flFragment, secondFragment)
                // Thêm vào stack
                addToBackStack(null)
                commit()
            }
        }
    }
}