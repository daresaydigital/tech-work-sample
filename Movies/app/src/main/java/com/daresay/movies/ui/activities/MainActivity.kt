package com.daresay.movies.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.daresay.movies.R
import com.daresay.movies.data.models.authentication.SessionRequestBody
import com.daresay.movies.data.user.UserData
import com.daresay.movies.databinding.ActivityMainBinding
import com.daresay.movies.ui.viewmodels.AuthenticationViewModel
import com.daresay.movies.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: AuthenticationViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // If we have a request token set we will go go home screen
        if (UserData(this).sessionToken == null) {
            // We don't have a session token, so start the process of getting one
            setUpRequestTokenObserver()
        }
        else {
            moveToHome()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        // Check for incoming intent data from authentication
        intent?.data?.let {
            parseUrlData(it)
        }
    }

    /**
     * Here we will parse the uri from the intent after authenticating a user.
     * If the authentication was successful, we will move to home screen.
     */
    private fun parseUrlData(uri: Uri) {
        // Check if the request was approved
        val approved = uri.getBooleanQueryParameter("approved", false)
        if (approved) {
            // Get the request_token and save it to shared_preferences
            val requestToken = uri.getQueryParameter("request_token")
            if (requestToken != null) {
                setUpSessionTokenObserver(requestToken)
            }
        }
        else {
            // TODO: Show error dialog
        }
    }

    /**
     * Here we retrieve the user request token so we can make further API calls.
     * After getting request token we will open up with tmdb authentication site and wait for user authentication.
     * We will save the token in SharedPreferences for easier and automatic access by other API calls.
     */
    private fun setUpRequestTokenObserver() {
        viewModel.userToken.observe(this, { it ->
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { token ->
                        openAuthenticationUrl(token.request_token)
                    }
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        })
    }

    /**
     * Using the request token from above method we will fetch the session token so that we can use the api.
     */
    private fun setUpSessionTokenObserver(requestToken: String) {
        viewModel.createSession(SessionRequestBody(requestToken)).observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { sessionToken ->
                        if (sessionToken.success) {
                            UserData(this).sessionToken = sessionToken.session_id
                            moveToHome()
                        }
                    }
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        })
    }

    /**
     * Open browser to authenticate the request token.
     * Read deep link data in @onNewIntent
     */
    private fun openAuthenticationUrl(requestToken: String) {
        val url = "https://www.themoviedb.org/authenticate/${requestToken}?redirect_to=movies://authentication/?status=1"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    /**
     * Navigate to the home screen.
     * This can be called straight away if user has a request token set already, or after authenticating a new request token.
     */
    private fun moveToHome() {
        findNavController(R.id.nav_host).navigate(R.id.homeViewPagerFragment)
    }
}