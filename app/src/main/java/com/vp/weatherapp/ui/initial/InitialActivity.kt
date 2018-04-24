package com.vp.weatherapp.ui.initial

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.vp.weatherapp.R
import com.vp.weatherapp.di.Params.INITIAL_VIEW
import com.vp.weatherapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_initial.*
import org.koin.android.ext.android.inject


class InitialActivity : AppCompatActivity(), InitialContract.View {


    override val presenter: InitialContract.Presenter by inject { mapOf(INITIAL_VIEW to this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
        setupStatusBar()
        presenter.initializeDatabase()
    }

    private fun setupStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onStop() {
        presenter.stop()
        super.onStop()
    }

    override fun onProgress(percent: Int) {
        progressBar.progress = percent
    }

    override fun onComplete() {
        val prefs = getSharedPreferences(MainActivity.PREFS_FILENAME, 0)
        prefs.edit().putBoolean(MainActivity.DATABASE_INITIALIZED, true).apply()
        startActivity(MainActivity.newIntent(this))
        finish()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, InitialActivity::class.java)
        }
    }
}