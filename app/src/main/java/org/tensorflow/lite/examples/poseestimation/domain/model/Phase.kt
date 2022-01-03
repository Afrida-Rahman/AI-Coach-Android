package org.tensorflow.lite.examples.poseestimation.domain.model

data class Phase(
    val phaseNumber: Int,
    val constraints: List<Constraint>,
    val holdTime: Int
)