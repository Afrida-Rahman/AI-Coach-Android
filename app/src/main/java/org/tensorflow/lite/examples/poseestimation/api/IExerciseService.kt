package org.tensorflow.lite.examples.poseestimation.api

import org.tensorflow.lite.examples.poseestimation.api.request.*
import org.tensorflow.lite.examples.poseestimation.api.resp.PatientExerciseKeypointResponse
import org.tensorflow.lite.examples.poseestimation.api.response.ExerciseTrackingResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IExerciseService {

    @POST("/api/exercisekeypoint/GetPatientExercise")
    fun getPatientExercise(@Body requestPayload: PatientDataPayload): Call<PatientExerciseKeypointResponse>

    @Headers("Authorization: Bearer YXBpdXNlcjpZV2xoYVlUUmNHbDFjMlZ5T2lRa1RVWVRFUk1ESXc=")
    @POST("/api/exercise/SaveExerciseTracking")
    fun saveExerciseData(@Body requestPayload: ExerciseTrackingPayload): Call<ExerciseTrackingResponse>

}