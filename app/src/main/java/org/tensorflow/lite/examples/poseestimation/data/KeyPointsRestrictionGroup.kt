package org.tensorflow.lite.examples.poseestimation.data

data class KeyPointsRestrictionGroup(
    val KeyPointsRestriction: List<KeyPointsRestriction>,
    val Phase: Int
)