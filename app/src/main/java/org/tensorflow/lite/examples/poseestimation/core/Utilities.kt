package org.tensorflow.lite.examples.poseestimation.core

import org.tensorflow.lite.examples.poseestimation.domain.model.APiUrl
import java.util.*
import kotlin.math.acos
import kotlin.math.sqrt

object Utilities {
    fun angle(
        startPoint: Point,
        middlePoint: Point = Point(0f, 0f),
        endPoint: Point = Point(1f, 0f),
        clockWise: Boolean = false
    ): Float {
        if ((middlePoint != Point(0f, 0f)) && (endPoint != Point(1f, 0f))) {
            val vectorBA = Point(startPoint.x - middlePoint.x, startPoint.y - middlePoint.y)
            val vectorBC = Point(endPoint.x - middlePoint.x, endPoint.y - middlePoint.y)
            val vectorBAAngle = angle(vectorBA)
            val vectorBCAngle = angle(vectorBC)
            var angleValue = if (vectorBAAngle > vectorBCAngle) {
                vectorBAAngle - vectorBCAngle
            } else {
                360 + vectorBAAngle - vectorBCAngle
            }
            if (clockWise) {
                angleValue = 360 - angleValue
            }
            return angleValue
        } else {
            val x = startPoint.x
            val y = startPoint.y
            val magnitude = sqrt((x * x + y * y).toDouble())
            var angleValue = if (magnitude >= 0.0001) {
                acos(x / magnitude)
            } else {
                0
            }
            angleValue = Math.toDegrees(angleValue.toDouble())
            if (y < 0) {
                angleValue = 360 - angleValue
            }
            return angleValue.toFloat()
        }
    }

    fun currentDate(): String {
        val currentDate = Calendar.getInstance()
        val day = currentDate.get(Calendar.DATE)
        val month = currentDate.get(Calendar.MONTH)
        val year = currentDate.get(Calendar.YEAR)
        return "$month/$day/$year"
    }

    fun getUrl(tenant: String): APiUrl {
        val tenantName = tenant.lowercase()
        val getPatientExerciseURL: String
        val saveExerciseTrackingURL: String
        val getExerciseKeyPointURL: String

        if (tenantName == "stg") {
            getPatientExerciseURL = "https://stgvaapi.injurycloud.com"
            getExerciseKeyPointURL = "https://stgvaapi.injurycloud.com"
            saveExerciseTrackingURL = "https://stgapi.injurycloud.com"
        } else {
            if (tenantName == "dev") {
                getPatientExerciseURL = "https://devvaapi.injurycloud.com"
                getExerciseKeyPointURL = "https://devvaapi.injurycloud.com"
                saveExerciseTrackingURL = "https://devapi.injurycloud.com"
            } else {
                getPatientExerciseURL = "https://vaapi.injurycloud.com"
                getExerciseKeyPointURL = "https://vaapi.injurycloud.com"
                saveExerciseTrackingURL = "https://api.injurycloud.com"
            }
        }

        return APiUrl(getPatientExerciseURL, getExerciseKeyPointURL, saveExerciseTrackingURL)
    }
}
