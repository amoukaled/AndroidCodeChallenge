package com.amoukaled.androidcodechallenge.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amoukaled.androidcodechallenge.databinding.FragmentTimerBinding
import com.amoukaled.androidcodechallenge.utils.CustomDateUtils
import com.amoukaled.androidcodechallenge.viewModels.TimerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerFragment : Fragment() {

    private var binding: FragmentTimerBinding? = null
    private lateinit var mainHandler: Handler
    private val model: TimerViewModel by viewModels()

    /**
     * Runnable that updates the UI and
     * delay one second before running again.
     */
    private val updateTime = object : Runnable {
        override fun run() {
            updateTimerClock()
            mainHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        mainHandler = Handler(Looper.getMainLooper())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Post the first update
        mainHandler.post(updateTime)
        initFirstLoginTV()
    }

    /**
     * Initializes the [FragmentTimerBinding.firstLoginTV]
     */
    private fun initFirstLoginTV() {
        model.firstLogin?.let { timestamp ->
            binding?.apply {
                firstLoginTV.text = CustomDateUtils.convertTimestampToFormattedDate(timestamp)
            }
        }
    }


    /**
     * Updates the UI.
     */
    private fun updateTimerClock() {
        binding?.let {
            val timeStr = CustomDateUtils.getTimeFromTimestamp(model.currentTimeSpent())
            it.timerTV.text = timeStr
        }
    }

    // On pause, remove the callback.
    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTime)
    }

    // On resume, post the runnable.
    override fun onResume() {
        super.onResume()
        mainHandler.post(updateTime)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        mainHandler.removeCallbacks(updateTime)
    }

}