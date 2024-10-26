package com.vicki.android

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vicki.android.data.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CatViewModel : ViewModel() {
    private val _catState = MutableStateFlow(Cat())
    val catState: StateFlow<Cat> = _catState.asStateFlow()

    private val _wordState = MutableStateFlow("")
    val wordState: StateFlow<String> = _wordState.asStateFlow()

    init {
       fetchCatFact()
    }

    fun fetchCatFact() {
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

    fun callLordByron() {
        val words = listOf(
            "Truth is stranger than fiction",
            "Love will find a way through paths where wolves fear to prey",
            "Like the measles, love is most dangerous when it comes late in life",
            "There is no instinct like that of the heart",
            "Adversity is the first path to truth",
            "Pleasure's a sin, and sometimes sin's a pleasure",
            "Absence—that common cure of love",
            "There is something pagan in me that I cannot shake off. In short, I deny nothing, but doubt everything",
            "If I don't write to empty my mind, I go mad"
        )

        _wordState.value = words.random()
    }
}
