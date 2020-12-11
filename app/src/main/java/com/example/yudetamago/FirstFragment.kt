package com.example.yudetamago

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var buttons: Array<ImageButton?> = arrayOfNulls(3)

        val softboiledButton = view.findViewById<ImageButton>(R.id.softboiledButton)
        val mediumboiledButton = view.findViewById<ImageButton>(R.id.mediumboiledButton)
        val hardboiledButton = view.findViewById<ImageButton>(R.id.hardboiledButton)
        val sizeRadioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)

        var boilTempTime: Int = 0
        var sizeTime: Int = -100 //sizeTimeが選ばれているか判定するために-20未満にする
        var boilTime: Int = 0

        buttons[0] = softboiledButton
        buttons[1] = mediumboiledButton
        buttons[2] = hardboiledButton

        softboiledButton.setOnClickListener{
            setBackGroundStyle(buttons,softboiledButton)
            boilTempTime = 360 //6min
        }
        mediumboiledButton.setOnClickListener{
            setBackGroundStyle(buttons,mediumboiledButton)
            boilTempTime = 450 //7min30s
        }
        hardboiledButton.setOnClickListener {
            setBackGroundStyle(buttons,hardboiledButton)
            boilTempTime = 600 //10min
        }

        sizeRadioGroup.setOnCheckedChangeListener{sizeRadioGroup,checkedId: Int->
            when(checkedId){
                R.id.smallButton->sizeTime =  -20
                R.id.largeButton->sizeTime = 20
                else -> sizeTime = 0
            }
        }

        view.findViewById<Button>(R.id.cook_button).setOnClickListener {
            boilTime = boilTempTime + sizeTime
            if((boilTempTime!=0) and (sizeTime>=-20)) {
                val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(boilTime)
                findNavController().navigate(action)
            }
            else if((boilTempTime==0) and (sizeTime>=-20)){
                Toast.makeText(context,"茹で加減を選んで下さい",Toast.LENGTH_SHORT).show()
            }
            else if((boilTempTime!=0) and (sizeTime<-20)){
                Toast.makeText(context,"卵のサイズを選んで下さい",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"茹で加減と卵のサイズを選んで下さい",Toast.LENGTH_SHORT).show()
            }
        }
    }
    //選択されてるやつに色付ける　クリックされた画像だけtrueになるようにする
    private fun setBackGroundStyle(buttons: Array<ImageButton?>,clickedButton: ImageButton){
        for (v in buttons) {
            v?.let{v.setSelected(false)}
        }
        clickedButton.setSelected(true)
    }
}
