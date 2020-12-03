package com.udemy.roommodeldemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udemy.roommodeldemo.databinding.ActivityMainBinding
import com.udemy.roommodeldemo.db.SubscriberDatabase
import com.udemy.roommodeldemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel

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
    }

    private fun initRecyclerView() {
        binding.apply {
            rvSubscriber.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            displaySubscribersList()
        }
    }

    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this, {
            binding.rvSubscriber.adapter = SubscriberAdapter(it)
        })
    }
}