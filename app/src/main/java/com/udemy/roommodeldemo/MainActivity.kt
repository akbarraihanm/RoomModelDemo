package com.udemy.roommodeldemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udemy.roommodeldemo.databinding.ActivityMainBinding
import com.udemy.roommodeldemo.db.Subscriber
import com.udemy.roommodeldemo.db.SubscriberDatabase
import com.udemy.roommodeldemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var subscriberAdapter: SubscriberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory)[
                SubscriberViewModel::class.java
        ]
        binding.subscriberViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
        subscriberViewModel.message.observe(this, {
            it.getContentIfNotHandled().let { msg ->
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initRecyclerView() {
        subscriberAdapter = SubscriberAdapter {
                selectedItem: Subscriber -> itemClicked(selectedItem)
        }
        binding.apply {
            rvSubscriber.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            binding.rvSubscriber.adapter = subscriberAdapter
            displaySubscribersList()
        }
    }

    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this, {
            subscriberAdapter.setList(it)
            subscriberAdapter.notifyDataSetChanged()
        })
    }

    private fun itemClicked(subscriber: Subscriber) {
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}