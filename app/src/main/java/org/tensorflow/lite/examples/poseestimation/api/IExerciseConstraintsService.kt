package org.tensorflow.lite.examples.poseestimation.api

import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseRequestPayload
import org.tensorflow.lite.examples.poseestimation.api.response.KeyPointRestrictions
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IExerciseConstraintsService {

    @POST("/api/exercisekeypoint/GetKeyPointsRestriction")
    fun getConstraint(@Body requestPayload: ExerciseRequestPayload): Call<KeyPointRestrictions>
}