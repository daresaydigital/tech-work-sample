package com.ukhanoff.rainbeforeseven

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ukhanoff.rainbeforeseven.fragment.WeatherFragment
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper



class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)
        showFragment(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }




    private fun showFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {

            val fragment = WeatherFragment()

//            val bundle = Bundle()
//            bundle.putString(UserProfileFragment.UID_KEY, USER_LOGIN)
//            fragment.setArguments(bundle)

            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, null)
                    .commit()
        }
    }

}
