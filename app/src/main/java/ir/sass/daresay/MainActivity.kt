package ir.sass.daresay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.daresay.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var dataBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
        setContentView(dataBinding.root)
    }

}