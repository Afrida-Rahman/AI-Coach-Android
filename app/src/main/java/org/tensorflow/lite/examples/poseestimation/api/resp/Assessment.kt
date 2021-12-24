package org.tensorflow.lite.examples.poseestimation.api.resp

data class Assessment(
    val exercises: List<Exercise>,
    val testId: String
)