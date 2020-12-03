package com.udemy.roommodeldemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udemy.roommodeldemo.databinding.SubscriberItemBinding
import com.udemy.roommodeldemo.db.Subscriber

class SubscriberAdapter(private val listSubs: List<Subscriber>) :
    RecyclerView.Adapter<SubscriberViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val binding: SubscriberItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.subscriber_item, parent, false)
        return SubscriberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bind(listSubs[position])
    }

    override fun getItemCount(): Int {
        return listSubs.size
    }
}

class SubscriberViewHolder(private val binding: SubscriberItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Subscriber) {
        binding.apply {
            with(subscriber) {
                tvSubscriberName.text = name
                tvSubscriberEmail.text = email
            }
        }
    }
}