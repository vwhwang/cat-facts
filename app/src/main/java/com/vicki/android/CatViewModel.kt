package com.vicki.android

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vicki.android.ui.theme.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CatViewModel : ViewModel() {
    private val _catState = MutableStateFlow(Cat())
    val catState: StateFlow<Cat> = _catState.asStateFlow()

    init {
       fetchCatFact()
    }

    private fun fetchCatFact() {
        viewModelScope.launch {
            val response = try {
                RetrofitInstance.api.fetchBlog()
            } catch (e: IOException ) {
                Log.d(TAG, "You may not have internet available.")
                return@launch
            } catch (e: HttpException) {
                Log.d(TAG, "bad request")
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                _catState.value = response.body() ?: Cat()
            } else {
                Log.d(TAG, "response from API is ${response.body()}")
            }
        }
    }
}
