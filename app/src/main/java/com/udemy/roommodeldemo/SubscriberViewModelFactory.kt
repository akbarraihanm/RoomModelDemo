package com.udemy.roommodeldemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udemy.roommodeldemo.db.SubscriberRepository

class SubscriberViewModelFactory(private val repo: SubscriberRepository):
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriberViewModel::class.java)) {
            return SubscriberViewModel(repo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}