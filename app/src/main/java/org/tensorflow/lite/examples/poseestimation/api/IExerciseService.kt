package org.tensorflow.lite.examples.poseestimation.api

import org.tensorflow.lite.examples.poseestimation.api.request.AssessmentListRequestPayload
import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseRequestPayload
import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseTrackingPayload
import org.tensorflow.lite.examples.poseestimation.api.response.AssessmentListResponse
import org.tensorflow.lite.examples.poseestimation.api.response.ExerciseTrackingResponse
import org.tensorflow.lite.examples.poseestimation.api.response.KeyPointRestrictions
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IExerciseService {

    @POST("/api/exercisekeypoint/GetPatientAssessments")
    fun getAssessmentList(@Body requestPayload: AssessmentListRequestPayload): Call<AssessmentListResponse>

    @POST("/api/exercisekeypoint/GetKeyPointsRestriction")
    fun getExerciseConstraint(@Body requestPayload: ExerciseRequestPayload): Call<KeyPointRestrictions>

    @Headers("Authorization: Bearer YXBpdXNlcjpZV2xoYVlUUmNHbDFjMlZ5T2lRa1RVWVRFUk1ESXc=")
    @POST("/api/exercise/SaveExerciseTracking")
    fun saveExerciseData(@Body requestPayload: ExerciseTrackingPayload): Call<ExerciseTrackingResponse>

}