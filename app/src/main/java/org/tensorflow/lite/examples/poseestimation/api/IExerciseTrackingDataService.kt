package org.tensorflow.lite.examples.poseestimation.api

import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseTrackingPayload
import retrofit2.http.Body
import retrofit2.http.POST

interface IExerciseTrackingDataService {

    @POST("/api/exercisekeypoint/GetKeyPointsRestriction")
    fun getConstraint(@Body requestPayload: ExerciseTrackingPayload)
}