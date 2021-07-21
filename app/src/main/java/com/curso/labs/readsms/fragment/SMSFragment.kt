package com.curso.labs.readsms.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.curso.labs.readsms.R
import com.curso.labs.readsms.databinding.SmsFragmentBinding

class SMSFragment : Fragment() {
    private lateinit var viewModel: SMSViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: SmsFragmentBinding = DataBindingUtil.inflate(inflater,
                                                                    R.layout.sms_fragment,
                                                                    container,
                                                                    false)

        val adapter = SmsAdapter()
        val application = requireNotNull(this.activity).application
        val viewModelFactory = SMSViewModelFactory(application)
        //val manager = GridLayoutManager(activity, 2)
        val manager = LinearLayoutManager(activity)
        binding.SmsList.layoutManager = manager

        viewModel = ViewModelProvider(this, viewModelFactory).get(SMSViewModel::class.java)
        binding.smsViewModel = viewModel
        binding.lifecycleOwner = this
        binding.SmsList.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }

}