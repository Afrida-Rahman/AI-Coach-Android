package org.tensorflow.lite.examples.poseestimation.domain.model

data class CountState(
    val calculatedAngle: Float,
    val angleMin: Int,
    val angleMax: Int,
    val uniqueId: Int
)