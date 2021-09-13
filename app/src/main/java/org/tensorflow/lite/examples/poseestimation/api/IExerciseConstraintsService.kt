package org.tensorflow.lite.examples.poseestimation.api

import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseRequestPayload
import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseTrackingPayload
import org.tensorflow.lite.examples.poseestimation.api.response.KeyPointRestrictions
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IExerciseService {

    @POST("/api/exercisekeypoint/GetKeyPointsRestriction")
    fun getConstraint(@Body requestPayload: ExerciseRequestPayload): Call<KeyPointRestrictions>

    @POST("/api/exercisekeypoint/GetKeyPointsRestriction")
    fun saveExerciseData(@Body requestPayload: ExerciseTrackingPayload)
}