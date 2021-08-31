package org.tensorflow.lite.examples.poseestimation.data

import org.tensorflow.lite.examples.poseestimation.core.AudioPlayer
import org.tensorflow.lite.examples.poseestimation.exercise.*

class ExerciseList(private val audioPlayer: AudioPlayer) {
    fun getExercise(): List<Exercise> {
        return mutableListOf(
            KneeSquat(audioPlayer),
            HalfSquat(audioPlayer),
            ReachArmsOverHand(audioPlayer),
            SeatedKneeExtension(audioPlayer)
        )
    }
}