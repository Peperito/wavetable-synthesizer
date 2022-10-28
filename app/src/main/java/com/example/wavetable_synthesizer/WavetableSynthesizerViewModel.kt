package com.example.wavetable_synthesizer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.math.exp
import kotlin.math.ln

class WavetableSynthesizerViewModel : ViewModel() {
    var wavetableSynthesizer: WavetableSynthesizer? = null

    set(value) {
        field = value
        applyParameters()
    }

    private val _frequency = MutableLiveData(300F)
    val frequency: LiveData<Float>
        get() {
            return _frequency
        }

    fun setFrequencySliderPosition(frequencySliderPosition: Float) {
        val frequencyInHz = frequencyInHzFromSliderPosition(frequencySliderPosition)
        _frequency.value = frequencyInHz
        viewModelScope.launch {
            wavetableSynthesizer?.setFrequency(frequencyInHz)
        }
    }

    private val frequencyRange = 40F..3000F
    private fun frequencyInHzFromSliderPosition(sliderPosition: Float): Float {
        val rangePosition = linearToExponential(sliderPosition)
        return valueFromRangePosition(frequencyRange, rangePosition)
    }

    fun sliderPositionFromFrequencyInHz(frequencyInHz: Float): Float {
        val rangePosition = rangePositionFromValue(frequencyRange, frequencyInHz)
        return exponentialToLinear(rangePosition)
    }

    companion object LinearToExponentialConverter {
        private const val MINIMUM_VALUE = 0.001F

        fun linearToExponential(value: Float): Float {
            assert(value in 0f..1f)

            return if (value < MINIMUM_VALUE) 0f else {
                exp(ln(MINIMUM_VALUE) - ln(MINIMUM_VALUE) * value)
            }
        }

        fun valueFromRangePosition(range: ClosedFloatingPointRange<Float>, rangePosition: Float ) =
            range.start + (range.endInclusive - range.start) * rangePosition

        fun rangePositionFromValue(range: ClosedFloatingPointRange<Float>, value: Float): Float {
            assert(value in range)
            return (value - range.start) / (range.endInclusive - range.start)
        }

        fun exponentialToLinear(rangePosition: Float): Float{
            assert(rangePosition in 0f..1f)
            return if(rangePosition < MINIMUM_VALUE) rangePosition else {
                (ln(rangePosition) - ln(MINIMUM_VALUE)) / (-ln(MINIMUM_VALUE))
            }
        }
    }

    private val _volume = MutableLiveData(-24f)
    val volume: LiveData<Float>
        get() {
            return _volume
        }
    val volumeRange = (-60f)..0f

    fun setVolume(volumeInDb: Float) {
        _volume.value = volumeInDb
        viewModelScope.launch {
            wavetableSynthesizer?.setVolume(volumeInDb)
        }
    }

    private var wavetable = Wavetable.SINE

    fun setWaveTable(newWavetable: Wavetable) {
        wavetable = newWavetable
        viewModelScope.launch {
           wavetableSynthesizer?.setWavetable(newWavetable)
        }
    }

    private val _playButtonLabel = MutableLiveData(R.string.play)
    val playButtonLabel: LiveData<Int>
        get() {
            return _playButtonLabel
        }

    fun playClicked() {
        viewModelScope.launch {
            if(wavetableSynthesizer?.isPlaying() == true) {
                wavetableSynthesizer?.stop()
            } else {
                wavetableSynthesizer?.play()
            }
            updatePlayButtonLabel()
        }
    }

    private fun updatePlayButtonLabel() {
        viewModelScope.launch {
            if(wavetableSynthesizer?.isPlaying() == true ) {
                _playButtonLabel.value = R.string.stop
            } else {
                _playButtonLabel.value = R.string.play
            }
        }
    }

    fun applyParameters() {
        viewModelScope.launch {
            wavetableSynthesizer?.setFrequency((frequency.value!!))
            wavetableSynthesizer?.setVolume((volume.value!!))
            wavetableSynthesizer?.setWavetable((wavetable))
            updatePlayButtonLabel()
        }
    }

}