package org.tensorflow.lite.examples.poseestimation.api.resp

data class KeyPointsRestrictionGroup(
    val KeyPointsRestriction: List<KeyPointsRestriction>,
    val Phase: Int
)