package com.segovia_jorge.proyectojimenez_segovia

import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FragmentDetailActivity : AppCompatActivity() {

    private lateinit var imgRank: ImageView
    private lateinit var txtSlot1Value1: TextView
    private lateinit var txtSlot1Value2: TextView
    private lateinit var txtSlot2Value1: TextView
    private lateinit var txtSlot2Value2: TextView
    private lateinit var txtSlot3Value: TextView
    private lateinit var seekBar1: SeekBar
    private lateinit var seekBar2: SeekBar
    private lateinit var seekBar3: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_detail)

        imgRank = findViewById(R.id.imgRank)
        txtSlot1Value1 = findViewById(R.id.txtSlot1Value1)
        txtSlot1Value2 = findViewById(R.id.txtSlot1Value2)
        txtSlot2Value1 = findViewById(R.id.txtSlot2Value1)
        txtSlot2Value2 = findViewById(R.id.txtSlot2Value2)
        txtSlot3Value = findViewById(R.id.txtSlot3Value)
        seekBar1 = findViewById(R.id.seekBar1)
        seekBar2 = findViewById(R.id.seekBar2)
        seekBar3 = findViewById(R.id.seekBar3)

        seekBar1.max = 100
        seekBar2.max = 100
        seekBar3.max = 100

        updateSlotValues()
        updateRankImage()

        seekBar1.setOnSeekBarChangeListener(createSeekBarChangeListener { updateSlotValues(); updateRankImage() })
        seekBar2.setOnSeekBarChangeListener(createSeekBarChangeListener { updateSlotValues(); updateRankImage() })
        seekBar3.setOnSeekBarChangeListener(createSeekBarChangeListener { updateSlotValues(); updateRankImage() })
    }

    private fun createSeekBarChangeListener(onProgressChanged: () -> Unit): SeekBar.OnSeekBarChangeListener {
        return object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                onProgressChanged()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        }
    }

    private fun updateSlotValues() {
        val progress1 = mapSeekBarProgressToPercentage(seekBar1.progress)
        val progress2 = mapSeekBarProgressToPercentage(seekBar2.progress)
        val progress3 = mapSeekBarProgressToPercentage(seekBar3.progress)

        txtSlot1Value1.text = "$progress1%"
        txtSlot1Value2.text = "$progress1%"
        txtSlot2Value1.text = "$progress2%"
        txtSlot2Value2.text = "$progress2%"
        txtSlot3Value.text = "$progress3%"

        updateTextColor(txtSlot1Value1, progress1)
        updateTextColor(txtSlot1Value2, progress1)
        updateTextColor(txtSlot2Value1, progress2)
        updateTextColor(txtSlot2Value2, progress2)
        updateTextColor(txtSlot3Value, progress3)
    }

    private fun mapSeekBarProgressToPercentage(progress: Int): Int {
        return 12 + ((progress * (25 - 12)) / 100)
    }

    private fun updateTextColor(textView: TextView, value: Int) {
        textView.setTextColor(
            when {
                value in 12..15 -> android.graphics.Color.RED
                value in 16..22 -> android.graphics.Color.YELLOW
                value in 23..25 -> android.graphics.Color.GREEN
                else -> android.graphics.Color.WHITE
            }
        )
    }

    private fun updateRankImage() {
        val progress1 = mapSeekBarProgressToPercentage(seekBar1.progress)
        val progress2 = mapSeekBarProgressToPercentage(seekBar2.progress)
        val progress3 = mapSeekBarProgressToPercentage(seekBar3.progress)

        val greenCount = listOf(progress1, progress2, progress3).count { it in 23..25 }

        imgRank.setImageResource(
            when (greenCount) {
                1 -> R.drawable.s_rank
                2 -> R.drawable.z_plus_rank
                3 -> R.drawable.godly_rank
                else -> R.drawable.default_rank
            }
        )
    }
}
