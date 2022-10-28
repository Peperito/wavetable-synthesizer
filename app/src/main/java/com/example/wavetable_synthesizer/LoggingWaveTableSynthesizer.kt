package com.example.wavetable_synthesizer

import android.util.Log

class LoggingWaveTableSynthesizer: WavetableSynthesizer {

    private var isPlaying = false
    private val TAG = "LogWavetableSynthetizer"

    override suspend fun play() {
        Log.d(TAG, "play() called")
        isPlaying = true
    }

    override suspend fun stop() {
        Log.d(TAG, "stop() called")
        isPlaying = false
    }

    override suspend fun isPlaying(): Boolean {
        Log.d(TAG, "isPlaying() called")
        return isPlaying
    }

    override suspend fun setFrequency(frequencyInHz: Float) {
        Log.d(TAG, "setFrequency() called at $frequencyInHz")

    }

    override suspend fun setVolume(volumeInDb: Float) {
        Log.d(TAG, "setVolume() called at $volumeInDb")
    }

    override suspend fun setWavetable(wavetable: Wavetable) {
        Log.d(TAG, "setWavetable() called with $wavetable")
    }

}