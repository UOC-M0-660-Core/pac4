package edu.uoc.pac4.ui.streams

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.pac4.data.network.UnauthorizedException
import edu.uoc.pac4.data.streams.model.Stream
import edu.uoc.pac4.data.streams.StreamsRepository
import kotlinx.coroutines.launch

/**
 * Created by alex on 12/09/2020.
 */

class StreamsViewModel(private val repository: StreamsRepository) : ViewModel() {

    // Observables
    val streams = MutableLiveData<List<Stream>>(emptyList())
    val isLoading = MutableLiveData<Boolean>(false)
    val isLoggedOut = MutableLiveData<Boolean>(false)

    private var cursor: String? = null

    /// Gets Streams
    fun getStreams(refresh: Boolean) {
        viewModelScope.launch {
            // Set Loading to true
            isLoading.postValue(true)
            // Get Streams
            try {
                val streamsResult = repository.getStreams(cursor = if (refresh) null else cursor)
                // Store cursor
                cursor = streamsResult.first
                // Set Streams Value
                if (refresh) {
                    // Set new list
                    streams.postValue(streamsResult.second)
                } else {
                    // Append to current list
                    val currentStreams = streams.value.orEmpty()
                    val totalStreams = currentStreams.plus(streamsResult.second)
                    streams.postValue(totalStreams)
                }
            } catch (e: UnauthorizedException) {
                isLoggedOut.postValue(true)
            }
            // Set Loading to false
            isLoading.postValue(false)
        }
    }

    /// Expose if more streams are available for pagination listener
    fun areMoreStreamsAvailable(): Boolean = cursor != null
}