package edu.uoc.pac4.ui.streams

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.pac4.data.network.OAuthException
import edu.uoc.pac4.data.streams.StreamsRepository
import edu.uoc.pac4.data.streams.model.Stream
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by alex on 12/09/2020.
 */

class StreamsViewModel(
    private val repository: StreamsRepository,
) : ViewModel() {

    private val TAG = "StreamsViewModel"

    // Observables
    val streams = MutableLiveData<List<Stream>>(emptyList())
    val isLoading = MutableLiveData<Boolean>(false)
    val isLoggedOut = MutableLiveData<Boolean>(false)

    private var cursor: String? = null

    init {
        getInitialStreams(forceRefresh = false)
    }

    // Gets initial streams
    // Used when screen starts or on refresh
    fun getInitialStreams(forceRefresh: Boolean) {
        cursor = null
        isLoading.postValue(true)
        viewModelScope.launch {
            repository.fetchInitialStreams(forceRefresh)
                .catch {
                    Log.i(TAG, "Got error getting initial streams: $it")
                    // Handle error
                    if (it is OAuthException) {
                        isLoggedOut.postValue(true)
                    }
                    isLoading.postValue(false)
                }.collect {
                    Log.i(TAG, "Got ${it.second.count()} initial streams with cursor ${it.first}")
                    // Emit
                    streams.postValue(it.second)
                    // Save cursor
                    cursor = it.first
                    isLoading.postValue(false)
                }
        }
    }

    /// Gets More Streams
    fun getMoreStreams() {
        // Set Loading to true
        isLoading.value = true
        viewModelScope.launch {
            // Get Streams
            try {
                val streamsResult = repository.fetchMoreStreams(cursor)
                Log.i(
                    TAG,
                    "Got ${streamsResult.second.count()} more streams with cursor ${streamsResult.first}"
                )
                // Store cursor
                cursor = streamsResult.first
                // Append to current streams list
                val currentStreams = streams.value.orEmpty()
                val totalStreams = currentStreams.plus(streamsResult.second)
                streams.postValue(totalStreams)
            } catch (e: OAuthException) {
                isLoggedOut.postValue(true)
            } catch (t: Throwable) {
                Log.w("StreamsViewModel", "Unhandled error: $t")
            }
            // Set Loading to false
            isLoading.postValue(false)
        }
    }

    /// Expose if more streams are available for pagination listener
    fun areMoreStreamsAvailable(): Boolean = cursor != null
}