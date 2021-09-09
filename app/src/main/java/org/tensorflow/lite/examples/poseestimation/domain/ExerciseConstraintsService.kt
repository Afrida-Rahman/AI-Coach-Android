package org.tensorflow.lite.examples.poseestimation.domain

import android.util.Log
import org.tensorflow.lite.examples.poseestimation.api.IExerciseConstraintsService
import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseData
import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseRequestPayload
import org.tensorflow.lite.examples.poseestimation.api.response.KeyPointRestrictions
import org.tensorflow.lite.examples.poseestimation.domain.model.Constraint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExerciseConstraintsService(
    private val tenant: String,
    private val exerciseId: Int
) {
    private var url: String = "https://vaapi.injurycloud.com"

    fun getExerciseConstraints(): List<Constraint> {
        val constraints = mutableListOf<Constraint>()
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
            .create(IExerciseConstraintsService::class.java)
        val requestPayload = ExerciseRequestPayload(
            Tenant = tenant,
            KeyPointsRestrictions = listOf(
                ExerciseData(exerciseId)
            )
        )
        val response = service.getConstraint(requestPayload)
        response.enqueue(object : Callback<KeyPointRestrictions> {
            override fun onResponse(
                call: Call<KeyPointRestrictions>,
                response: Response<KeyPointRestrictions>
            ) {
                val responseBody = response.body()!!
                responseBody[0].KeyPointsRestrictionGroup.forEach { group ->
                    group.KeyPointsRestriction.forEach { restriction ->
                        constraints.add(
                            Constraint(
                                phase = restriction.Phase,
                                minValue = restriction.MinValidationValue,
                                maxValue = restriction.MaxValidationValue
                            )
                        )
                    }
                }
                Log.d("ExerciseActivityTag", "$constraints")
            }

            override fun onFailure(call: Call<KeyPointRestrictions>, t: Throwable) {
                Log.d("retrofit", "on failure ::: " + t.message)
            }
        })
        return constraints
    }
}