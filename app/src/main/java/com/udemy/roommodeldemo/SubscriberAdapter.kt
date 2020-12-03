package com.udemy.roommodeldemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udemy.roommodeldemo.databinding.SubscriberItemBinding
import com.udemy.roommodeldemo.db.Subscriber

class SubscriberAdapter(private val clickListener: (Subscriber) -> Unit) :
    RecyclerView.Adapter<SubscriberViewHolder>() {


    private val listSubs = arrayListOf<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val binding: SubscriberItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.subscriber_item, parent, false)
        return SubscriberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bind(listSubs[position], clickListener)
    }

    override fun getItemCount(): Int {
        return listSubs.size
    }

    fun setList(subscriber: List<Subscriber>) {
        listSubs.clear()
        listSubs.addAll(subscriber)
    }
}

class SubscriberViewHolder(private val binding: SubscriberItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
        binding.apply {
            subscriber.apply {
                tvSubscriberName.text = name
                tvSubscriberEmail.text = email
                lLayoutSubscriberItem.setOnClickListener { clickListener(this) }
            }
        }
    }
}