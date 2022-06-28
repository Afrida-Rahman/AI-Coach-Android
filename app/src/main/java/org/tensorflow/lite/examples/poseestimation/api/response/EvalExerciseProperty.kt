package org.tensorflow.lite.examples.poseestimation.api.response

data class EvalExerciseProperty(
    val Id: Int,
    val DayNumber: Int,
    val DayName: String,
    val HoldInSeconds: Int,
    val RepetitionInCount: Int,
    val SetInCount: Int,
    val FrequencyInDay: Int
)