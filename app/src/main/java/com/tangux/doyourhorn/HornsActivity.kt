package com.tangux.doyourhorn

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.RoomDatabase
import com.tangux.doyourhorn.databinding.HornsListBinding

class HornsActivity : AppCompatActivity() {

    private lateinit var binding: HornsListBinding

    private val horns = listOf<Horn>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = HornsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}