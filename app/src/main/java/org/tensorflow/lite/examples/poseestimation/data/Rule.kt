package org.tensorflow.lite.examples.poseestimation.data

import android.graphics.Color
import org.tensorflow.lite.examples.poseestimation.core.Point


data class Rule(
    val type: RuleType,
    val startPoint: Point,
    val middlePoint: Point = Point(0f, 0f),
    val endPoint: Point,
    val clockWise: Boolean = false,
    val color: Int = Color.WHITE
)
