package org.tensorflow.lite.examples.poseestimation.api.resp

data class Exercise(
    val ExerciseId: Int,
    val ExerciseName: String,
    val FrequencyInDay: Int,
    val HoldInSeconds: Int,
    val ImageURLs: List<String>,
    val Instructions: String,
    val IsPhaseFinished: Boolean,
    val KeyPointsRestrictionGroup: List<KeyPointsRestrictionGroup>,
    val RepetitionInCount: Int,
    val SetInCount: Int
)