package org.tensorflow.lite.examples.poseestimation.api.response

data class Exercise(
    val ExerciseId: Int,
    val ExerciseMedia: String,
    val ProtocolId: Int,
    val ExerciseName: String,
    val Instructions: String,
    val ImageURLs: List<String>,
    val SetInCount: Int,
    val RepetitionInCount: Int,
    val FrequencyInDay: Int,
    val Phases: List<PhaseInfo1>,
    val active: Boolean = true
)