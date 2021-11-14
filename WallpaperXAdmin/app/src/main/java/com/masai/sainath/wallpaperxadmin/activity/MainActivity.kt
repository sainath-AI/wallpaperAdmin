package com.masai.sainath.wallpaperxadmin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.masai.sainath.wallpaperxadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Bom.setOnClickListener {
            startActivity(Intent(this@MainActivity, BomActivity::class.java))
        }
        binding.Tct.setOnClickListener {
            startActivity(Intent(this@MainActivity, TctActivity::class.java))

        }
        binding.CAt.setOnClickListener {
            startActivity(Intent(this@MainActivity, CatActivity::class.java))

        }
    }
}