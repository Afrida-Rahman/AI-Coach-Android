package org.tensorflow.lite.examples.poseestimation.domain.model

data class Constraint(
    val phase: Int,
    val minValue: Int,
    val maxValue: Int
)