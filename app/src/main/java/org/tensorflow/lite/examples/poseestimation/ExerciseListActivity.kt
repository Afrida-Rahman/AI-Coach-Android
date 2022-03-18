package org.tensorflow.lite.examples.poseestimation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import org.tensorflow.lite.examples.poseestimation.api.IExerciseService
import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseListRequestPayload
import org.tensorflow.lite.examples.poseestimation.api.response.ExerciseListResponse
import org.tensorflow.lite.examples.poseestimation.core.Exercises
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.databinding.ActivityExerciseListBinding
import org.tensorflow.lite.examples.poseestimation.exercise.home.GeneralExercise
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ExerciseListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseListBinding
    private lateinit var adapter: RecyclerView
    private var assignedExercises: List<HomeExercise> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private suspend fun getExerciseList(tenant: String, testId: String) {
        val client = OkHttpClient.Builder()
            .connectTimeout(4, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        val service = Retrofit.Builder()
            .baseUrl(Utilities.getUrl(tenant).getExerciseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IExerciseService::class.java)
        val requestPayload = ExerciseListRequestPayload(
            Tenant = tenant,
            TestId = testId
        )
        val response = service.getExerciseList(requestPayload)
        response.enqueue(object : Callback<ExerciseListResponse> {
            override fun onResponse(
                call: Call<ExerciseListResponse>,
                response: Response<ExerciseListResponse>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    if (responseBody.Exercises.isNotEmpty()) {
                        val implementedExerciseList = Exercises.get(this@ExerciseListActivity)
                        val parsedExercises = mutableListOf<HomeExercise>()
                        responseBody.Exercises.forEach { exercise ->
                            val implementedExercise =
                                implementedExerciseList.find { it.id == exercise.ExerciseId }

                            if (implementedExercise != null) {
                                implementedExercise.setExercise(
                                    exerciseName = exercise.ExerciseName,
                                    exerciseInstruction = exercise.Instructions,
                                    exerciseImageUrls = exercise.ImageURLs,
                                    exerciseVideoUrls = exercise.ExerciseMedia,
                                    repetitionLimit = exercise.RepetitionInCount,
                                    setLimit = exercise.SetInCount,
                                    protoId = exercise.ProtocolId
                                )
                                parsedExercises.add(implementedExercise)
                            } else {
                                val notImplementedExercise = GeneralExercise(
                                    context = this@ExerciseListActivity,
                                    exerciseId = exercise.ExerciseId,
                                    active = false
                                )
                                notImplementedExercise.setExercise(
                                    exerciseName = exercise.ExerciseName,
                                    exerciseInstruction = exercise.Instructions,
                                    exerciseImageUrls = exercise.ImageURLs,
                                    exerciseVideoUrls = exercise.ExerciseMedia,
                                    repetitionLimit = exercise.RepetitionInCount,
                                    setLimit = exercise.SetInCount,
                                    protoId = exercise.ProtocolId
                                )
                                parsedExercises.add(notImplementedExercise)
                            }
                        }
                        assignedExercises = parsedExercises
                    } else {
                        Toast.makeText(
                            this@ExerciseListActivity,
                            "Got empty response!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@ExerciseListActivity,
                        "Failed to get exercise list from API and got nothing!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                binding.progressIndicator.visibility = View.GONE
            }

            override fun onFailure(call: Call<ExerciseListResponse>, t: Throwable) {
                Toast.makeText(
                    this@ExerciseListActivity,
                    "Failed to get assessment list from API.",
                    Toast.LENGTH_LONG
                ).show()
                binding.progressIndicator.visibility = View.GONE
            }
        })
    }
}