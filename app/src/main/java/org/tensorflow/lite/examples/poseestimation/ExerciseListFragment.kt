package org.tensorflow.lite.examples.poseestimation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.tensorflow.lite.examples.poseestimation.api.IExerciseService
import org.tensorflow.lite.examples.poseestimation.api.request.ExerciseListRequestPayload
import org.tensorflow.lite.examples.poseestimation.api.response.ExerciseListResponse
import org.tensorflow.lite.examples.poseestimation.core.ExerciseListAdapter
import org.tensorflow.lite.examples.poseestimation.core.Exercises
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.exercise.home.GeneralExercise
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ExerciseListFragment(
    private val assessmentId: String,
    private val assessmentDate: String,
    private val patientId: String,
    private val tenant: String,
) : Fragment() {
    private lateinit var getExerciseListUrl: String
    private lateinit var adapter: RecyclerView
    private var assignedExercises: List<HomeExercise> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_list, container, false)
        adapter = view.findViewById(R.id.exercise_list_container)
        val displayTestId: TextView = view.findViewById(R.id.test_id_display)
        val displayTestDate: TextView = view.findViewById(R.id.test_date_display)
        val searchExercise: SearchView = view.findViewById(R.id.search_exercise)
        displayTestId.text = displayTestId.context.getString(R.string.test_id).format(assessmentId)
        displayTestDate.text =
            displayTestDate.context.getString(R.string.test_date).format(assessmentDate)

        CoroutineScope(Dispatchers.IO).launch {
            getExerciseList(tenant = tenant, testId = assessmentId)
        }

        searchExercise.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchQuery: String): Boolean {
                if (searchQuery.isNotEmpty()) {
                    adapter.adapter = ExerciseListAdapter(
                        testId = assessmentId,
                        testDate = assessmentDate,
                        exerciseList = assignedExercises.filter {
                            it.name.lowercase().contains(searchQuery.lowercase())
                        },
                        manager = parentFragmentManager,
                        patientId = patientId,
                        tenant = tenant
                    )
                    adapter.adapter?.notifyDataSetChanged()
                }
                searchExercise.clearFocus()
                return true
            }

            override fun onQueryTextChange(searchQuery: String): Boolean {
                if (searchQuery.isNotEmpty()) {
                    adapter.adapter = ExerciseListAdapter(
                        testId = assessmentId,
                        testDate = assessmentDate,
                        exerciseList = assignedExercises.filter {
                            it.name.lowercase().contains(searchQuery.lowercase())
                        },
                        manager = parentFragmentManager,
                        patientId = patientId,
                        tenant = tenant
                    )
                    adapter.adapter?.notifyDataSetChanged()
                }
                return true
            }
        })

        searchExercise.setOnCloseListener {
            Log.d("CheckCloseListener", "I am being called")
            adapter.adapter = ExerciseListAdapter(
                assessmentId,
                assessmentDate,
                assignedExercises,
                parentFragmentManager,
                patientId = patientId,
                tenant = tenant
            )
            adapter.adapter?.notifyDataSetChanged()
            searchExercise.clearFocus()
            true
        }


        return view
    }

    private fun getExerciseList(tenant: String, testId: String) {
        getExerciseListUrl = Utilities.getUrl(tenant).getExerciseUrl
        val client = OkHttpClient.Builder()
            .connectTimeout(4, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        val service = Retrofit.Builder()
            .baseUrl(getExerciseListUrl)
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
                        context?.let { ctx ->
                            val implementedExerciseList = Exercises.get(ctx)
                            var parsedExercises = mutableListOf<HomeExercise>()
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
                                        context = ctx,
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
                            parsedExercises =
                                parsedExercises.sortedBy { it.active }.reversed().toMutableList()
                            assignedExercises = parsedExercises
                            adapter.adapter = ExerciseListAdapter(
                                testId = testId,
                                testDate = assessmentDate,
                                exerciseList = parsedExercises,
                                manager = parentFragmentManager,
                                patientId = patientId,
                                tenant = tenant
                            )
                            adapter.adapter?.notifyDataSetChanged()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Got empty response!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Failed to get exercise list from API and got nothing!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ExerciseListResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Failed to get assessment list from API.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
