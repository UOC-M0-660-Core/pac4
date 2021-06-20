package edu.uoc.pac4.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.pac4.data.authentication.repository.AuthenticationRepository
import kotlinx.coroutines.launch

/**
 * Created by alex on 11/21/20.
 */

// This is a ViewModel, used to expose data to the UI
// It can interact with the different repositories
class LaunchViewModel(
    private val repository: AuthenticationRepository
) : ViewModel() {

    // User availability Observable
    val isUserAvailable = MutableLiveData<Boolean>()

    // Observable to expose loading status
    val isLoading = MutableLiveData<Boolean>()

    // Observable to expose errors to the user
    val error = MutableLiveData<String>()

    fun getUserAvailability() {
        // Set Loading to true
        isLoading.postValue(true)
        // Get Availability from Repository and post result
        viewModelScope.launch {
            try {
                // Ask the repository for user availability
                // (here the coroutine will "suspend" waiting for the response)
                val isUserAvailable = repository.isUserAvailable()
                // Post the response to the Live Data observable
                this@LaunchViewModel.isUserAvailable.postValue(isUserAvailable)
            } catch (t: Throwable) {
                // Post error
                error.postValue("Unknown error")
            }
            // Set Loading to false
            isLoading.postValue(false)
        }
    }
}