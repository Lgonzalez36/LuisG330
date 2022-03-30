/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.TitleFragmentBinding
import com.google.android.material.slider.Slider

/**
 * Fragment for the starting or title screen of the app
 */
class TitleFragment : Fragment() {
    private lateinit var binding: TitleFragmentBinding
    private var timerSet: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.title_fragment,
            container,
            false
        )

        setSlider()
        setSwitch()
        binding.playGameButton.setOnClickListener {
            check()
        }
        return binding.root
    }

    private fun setSwitch() {
        binding.timerSwtich.setOnCheckedChangeListener { compoundButton, isChecked ->
            timerSet = if (isChecked){
                val timer = binding.slider.value
                timer.toInt()
            } else{
                -1
            }
        }
    }

    private fun check() {
            if (timerSet > 0){
                Toast.makeText(context, "On:", Toast.LENGTH_SHORT).show()
                val timerInt = binding.slider.value
                findNavController().navigate(TitleFragmentDirections.actionTitleToGame(timerInt.toInt()))
            }
            else if (timerSet == -1 ){
                Toast.makeText(context, "Off:", Toast.LENGTH_SHORT).show()
                findNavController().navigate(TitleFragmentDirections.actionTitleToGame(-1))
            }
    }

    private fun setSlider() {
        binding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Responds to when slider's touch event is being started
                binding.setTimeText.text = binding.slider.value.toString()
            }

            override fun onStopTrackingTouch(slider: Slider) {
                // Responds to when slider's touch event is being stopped
                binding.setTimeText.text = binding.slider.value.toString()
            }
        })

        binding.slider.addOnChangeListener { slider, value, fromUser ->
            // Responds to when slider's value is changed
            binding.setTimeText.text = binding.slider.value.toString()
        }
    }
}
//findNavController().navigate(TitleFragmentDirections.actionTitleToGame(-1))