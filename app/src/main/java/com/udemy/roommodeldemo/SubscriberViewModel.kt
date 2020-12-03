package com.udemy.roommodeldemo

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udemy.roommodeldemo.db.Subscriber
import com.udemy.roommodeldemo.db.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repo: SubscriberRepository) : ViewModel(), Observable {

    val subscribers = repo.subscribers
    private var isUpdateOrDelete = false
    private lateinit var toUpdateOrDelete: Subscriber

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputEmail = MutableLiveData<String>()
    @Bindable
    val saveOrUpdateBtnText = MutableLiveData<String>()
    @Bindable
    val clearAllOrDeleteBtnText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateBtnText.value = "Save"
        clearAllOrDeleteBtnText.value = "Clear All"
    }

    fun saveOrUpdate() {

        if (inputName.value == null) {
            statusMessage.value = Event("Please enter the subscriber's name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter the subscriber's email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else {

            if (isUpdateOrDelete) {
                toUpdateOrDelete.name = inputName.value
                toUpdateOrDelete.email = inputEmail.value
                update(toUpdateOrDelete)
            } else {
                val name = inputName.value
                val email = inputEmail.value
                insert(Subscriber(0, name, email))
                inputName.value = null
                inputEmail.value = null
            }
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(toUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun insert(subscriber: Subscriber) = viewModelScope.launch {
        val newRowId = repo.insert(subscriber)
        if (newRowId > -1) {
            statusMessage.value = Event("Subscriber has been saved $newRowId")
        } else {
            statusMessage.value = Event("An error occurred")
        }
    }

    private fun update(subscriber: Subscriber) = viewModelScope.launch {
        val numberOfRow = repo.update(subscriber)
        if (numberOfRow > 0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateBtnText.value = "Save"
            clearAllOrDeleteBtnText.value = "Clear All"
            statusMessage.value = Event("$numberOfRow Subscriber has been updated")
        } else {
            statusMessage.value = Event("An error occurred")
        }
    }

    private fun delete(subscriber: Subscriber) = viewModelScope.launch {
        val deleted = repo.delete(subscriber)
        if (deleted > 0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateBtnText.value = "Save"
            clearAllOrDeleteBtnText.value = "Clear All"
            statusMessage.value = Event("Subscriber ${subscriber.name} has been deleted")
        } else {
            statusMessage.value = Event("An error occurred")
        }
    }

    private fun clearAll() = viewModelScope.launch {
        val deletedRow = repo.deleteAll()
        if (deletedRow > 0) {
            statusMessage.value = Event("$deletedRow subscriber has been deleted")
        } else {
            statusMessage.value = Event("An error occurred")
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        toUpdateOrDelete = subscriber
        saveOrUpdateBtnText.value = "Update"
        clearAllOrDeleteBtnText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}