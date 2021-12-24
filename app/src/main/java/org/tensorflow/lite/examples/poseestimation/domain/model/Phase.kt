package org.tensorflow.lite.examples.poseestimation.domain.model

data class Phase(
    val phaseNum: Int,
    val constraints: List<Constraint>
)