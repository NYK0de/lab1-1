package com.curso.labs.readsms.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.curso.labs.readsms.Sms
import com.curso.labs.readsms.databinding.SmsItemCardviewBinding

class SmsAdapter: ListAdapter<Sms, SmsAdapter.SMSItemViewHolder> (SmsDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSItemViewHolder {
        return SMSItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SMSItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SMSItemViewHolder(val binding: SmsItemCardviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Sms){
            binding.sms = item
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): SMSItemViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SmsItemCardviewBinding.inflate(layoutInflater, parent, false)
                return  SMSItemViewHolder(binding)
            }
        }
    }

    class SmsDiffCallback: DiffUtil.ItemCallback<Sms>(){
        override fun areItemsTheSame(oldItem: Sms, newItem: Sms): Boolean {
            return oldItem.number == newItem.number && oldItem.date == newItem.date
        }
        override fun areContentsTheSame(oldItem: Sms, newItem: Sms): Boolean {
            return oldItem == newItem
        }
    }
}