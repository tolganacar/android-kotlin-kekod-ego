package com.tolganacar.kekodego

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tolganacar.kekodego.databinding.FragmentMainpageBinding

class MainpageFragment : Fragment() {
    private lateinit var binding: FragmentMainpageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainpageBinding.inflate(inflater, container, false)

        setupSwitchListeners()

        return binding.root
    }

    private fun setupSwitchListeners() {
        binding.switchEgo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                disableOtherSwitches()
            } else {
                enableOtherSwitches()
            }
        }

        val otherSwitches = listOf(
            binding.switchHappiness,
            binding.switchOptimism,
            binding.switchKindness,
            binding.switchGiving,
            binding.switchRespect
        )

        otherSwitches.forEach { switch ->
            switch.setOnCheckedChangeListener { _, _ ->
                if (binding.switchEgo.isChecked) {
                    disableOtherSwitches()
                }
            }
        }
    }

    private fun disableOtherSwitches() {
        binding.switchHappiness.isChecked = false
        binding.switchOptimism.isChecked = false
        binding.switchKindness.isChecked = false
        binding.switchGiving.isChecked = false
        binding.switchRespect.isChecked = false
    }

    private fun enableOtherSwitches() {
        binding.switchHappiness.isEnabled = true
        binding.switchOptimism.isEnabled = true
        binding.switchKindness.isEnabled = true
        binding.switchGiving.isEnabled = true
        binding.switchRespect.isEnabled = true
    }

}