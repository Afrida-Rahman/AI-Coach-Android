package org.tensorflow.lite.examples.poseestimation

import org.tensorflow.lite.examples.poseestimation.data.KeyPointRestrictions
import org.tensorflow.lite.examples.poseestimation.data.PostedData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ExerciseConstraintApiInterface {
    @POST("GetKeyPointsRestriction")
    fun getConstraint(@Body postedData: PostedData): Call<KeyPointRestrictions>
}