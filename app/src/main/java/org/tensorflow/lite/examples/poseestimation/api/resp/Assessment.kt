package org.tensorflow.lite.examples.poseestimation.api.resp

data class Assessment(
    val Exercises: List<Exercise>,
    val TestId: String
)