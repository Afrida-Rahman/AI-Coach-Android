package org.tensorflow.lite.examples.poseestimation

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ExerciseConstraintApiInterface {

    @POST("GetKeyPointsRestriction")
    fun  getConstraint(@Body postedData : PostedData) : Call<List<KeyPointsRestriction>>

}