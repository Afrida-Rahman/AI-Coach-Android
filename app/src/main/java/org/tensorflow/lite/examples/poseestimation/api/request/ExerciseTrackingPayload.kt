package org.tensorflow.lite.examples.poseestimation.api.request

data class ExerciseTrackingPayload(
    val tenant: String,
    val exerciseId: Int,
    val rightCount: Int,
    val wrongCount: Int
)
