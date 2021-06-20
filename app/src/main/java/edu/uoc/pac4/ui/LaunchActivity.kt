package edu.uoc.pac4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import edu.uoc.pac4.R
import edu.uoc.pac4.ui.login.LoginActivity
import edu.uoc.pac4.ui.streams.StreamsActivity
import kotlinx.android.synthetic.main.activity_launch.*
import org.koin.android.viewmodel.ext.android.viewModel

class LaunchActivity : AppCompatActivity() {

    private val viewModel by viewModel<LaunchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        // Init observers for ViewModel LiveData
        initObservers()
        if(savedInstanceState == null) {
            // Initial instructions
            // We could also do this inside the ViewModel `init {}` function instead
            viewModel.getUserAvailability()
        }
    }

    private fun initObservers() {
        // Observe `isUserAvailable` LiveData
        viewModel.isUserAvailable.observe(this) {
            // Call a method when a new value is emitted
            onUserAvailable(it)
        }
        // Loading
        viewModel.isLoading.observe(this) {
            progressBar.visibility = if(it) View.VISIBLE else View.GONE
        }
        // Errors
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun onUserAvailable(isAvailable: Boolean) {
        if (isAvailable) {
            // User is available, open Streams Activity
            startActivity(Intent(this, StreamsActivity::class.java))
        } else {
            // User not available, request Login
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}