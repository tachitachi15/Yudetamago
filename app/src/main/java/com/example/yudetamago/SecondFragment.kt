package com.example.yudetamago

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.w3c.dom.Text


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

class SecondFragment : Fragment() {

    val args: SecondFragmentArgs by navArgs()
    val handler = Handler()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var timeValue = args.timerData
        val timeText = view.findViewById(R.id.timerView) as TextView
        val startButton = view.findViewById(R.id.start_button) as Button
        val stopButton = view.findViewById(R.id.stop_button) as Button

        timeText.text = timeToText(timeValue)

        val runnable = object : Runnable{
            override fun run(){
                timeValue--
                timeToText(timeValue)?.let{
                    timeText.text = it
                }
                handler.postDelayed(this, 1000)
            }
        }

        startButton.setOnClickListener{
            handler.post(runnable)
        }
        stopButton.setOnClickListener{
            handler.removeCallbacks(runnable)
        }

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
    private fun timeToText(time: Int=0) : String? {
        return if(time<0) {
            null
        }
        else if(time==0){
            "完成です"
        }
        else{
            val h = time/3600
            val m = time%3600/60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }
}