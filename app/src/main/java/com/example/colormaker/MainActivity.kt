// Name: William Phung
// CWID: 886287374
// Email: willism1769@csu.fullerton.edu

package com.example.colormaker

import android.os.Bundle
import android.widget.SeekBar
import android.widget.Button
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Declare variables for UI elements
    private lateinit var redSeekBar: SeekBar
    private lateinit var greenSeekBar: SeekBar
    private lateinit var blueSeekBar: SeekBar
    private lateinit var colorPreview: View
    private lateinit var resetButton: Button
    private lateinit var redSwitch: Switch
    private lateinit var greenSwitch: Switch
    private lateinit var blueSwitch: Switch
    private lateinit var redTextView: TextView
    private lateinit var greenTextView: TextView
    private lateinit var blueTextView: TextView

    // Initialize color component values
    private var redValue: Float = 0.0f
    private var greenValue: Float = 0.0f
    private var blueValue: Float = 0.0f
    private var lastValidRedProgress = 0
    private var lastValidGreenProgress = 0
    private var lastValidBlueProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        colorPreview = findViewById(R.id.colorPreview)
        redSeekBar = findViewById(R.id.redSeekBar)
        greenSeekBar = findViewById(R.id.greenSeekBar)
        blueSeekBar = findViewById(R.id.blueSeekBar)
        redSwitch = findViewById(R.id.redSwitch)
        greenSwitch = findViewById(R.id.greenSwitch)
        blueSwitch = findViewById(R.id.blueSwitch)
        redTextView = findViewById(R.id.redTextView)
        greenTextView = findViewById(R.id.greenTextView)
        blueTextView = findViewById(R.id.blueTextView)
        resetButton = findViewById(R.id.resetButton)

        // Set SeekBar change listeners
        redSeekBar.setOnSeekBarChangeListener(createOnSeekBarChangeListener { redValue = it })
        greenSeekBar.setOnSeekBarChangeListener(createOnSeekBarChangeListener { greenValue = it })
        blueSeekBar.setOnSeekBarChangeListener(createOnSeekBarChangeListener { blueValue = it })

        // Set initial state of color components
        redSwitch.isChecked = true
        greenSwitch.isChecked = true
        blueSwitch.isChecked = true
        onSwitchCheckedChanged(true, redSeekBar)
        onSwitchCheckedChanged(true, greenSeekBar)
        onSwitchCheckedChanged(true, blueSeekBar)

        // Set listeners for color component switches
        redSwitch.setOnCheckedChangeListener { _, isChecked -> onSwitchCheckedChanged(isChecked, redSeekBar) }
        greenSwitch.setOnCheckedChangeListener { _, isChecked -> onSwitchCheckedChanged(isChecked, greenSeekBar) }
        blueSwitch.setOnCheckedChangeListener { _, isChecked -> onSwitchCheckedChanged(isChecked, blueSeekBar) }

        // Set reset button click listener
        resetButton.setOnClickListener {
            resetColor()
        }

        // Initialize the UI with default values
        updateColorPreview()
    }

    // Create a SeekBar change listener
    private fun createOnSeekBarChangeListener(onProgressChanged: (Float) -> Unit): SeekBar.OnSeekBarChangeListener {
        return object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val value = progress / 100.0f
                    onProgressChanged(value)
                    updateColorPreview()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        }
    }

    // Handle changes in color component switches
    private fun onSwitchCheckedChanged(isChecked: Boolean, seekBar: SeekBar) {
        if (isChecked) {
            seekBar.progress = lastValidProgress(seekBar)
        } else {
            lastValidProgress(seekBar, 0)
            seekBar.progress = 0
        }
        seekBar.isEnabled = isChecked
        updateColorPreview()
    }

    // Get the last valid progress for a SeekBar
    private fun lastValidProgress(seekBar: SeekBar): Int {
        return when (seekBar) {
            redSeekBar -> lastValidRedProgress
            greenSeekBar -> lastValidGreenProgress
            blueSeekBar -> lastValidBlueProgress
            else -> 0
        }
    }

    // Set the last valid progress for a SeekBar
    private fun lastValidProgress(seekBar: SeekBar, progress: Int) {
        when (seekBar) {
            redSeekBar -> lastValidRedProgress = progress
            greenSeekBar -> lastValidGreenProgress = progress
            blueSeekBar -> lastValidBlueProgress = progress
        }
    }

    // Update the color preview based on SeekBar values
    private fun updateColorPreview() {
        val redProgress = String.format("%.2f", redValue)
        val greenProgress = String.format("%.2f", greenValue)
        val blueProgress = String.format("%.2f", blueValue)

        val color = android.graphics.Color.rgb(
            (redValue * 255).toInt(),
            (greenValue * 255).toInt(),
            (blueValue * 255).toInt()
        )
        colorPreview.setBackgroundColor(color)

        // Update the progress TextViews
        redTextView.text = redProgress
        greenTextView.text = greenProgress
        blueTextView.text = blueProgress
    }

    // Reset color components to initial values
    private fun resetColor() {
        redValue = 0.0f
        greenValue = 0.0f
        blueValue = 0.0f
        redSeekBar.progress = 0
        greenSeekBar.progress = 0
        blueSeekBar.progress = 0
        updateColorPreview()
    }
}
