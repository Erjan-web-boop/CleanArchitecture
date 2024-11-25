package com.example.cleanarchitecture.presentation.activity



import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode){
            KeyEvent.KEYCODE_VOLUME_UP ->{
                Log.e("erjan","Volume up")
            }
            KeyEvent.KEYCODE_VOLUME_DOWN ->{
                Log.e("erjan","Volume down")
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}