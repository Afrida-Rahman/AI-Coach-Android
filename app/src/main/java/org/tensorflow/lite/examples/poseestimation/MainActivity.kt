package org.tensorflow.lite.examples.poseestimation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.poseestimation.data.ExerciseConstraint
import org.tensorflow.lite.examples.poseestimation.data.ExerciseData
import org.tensorflow.lite.examples.poseestimation.data.KeyPointsRestriction
import org.tensorflow.lite.examples.poseestimation.data.PostedData
import org.tensorflow.lite.examples.poseestimation.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private var url: String = "https://vaapi.injurycloud.com/api/exercisekeypoint/"
    private lateinit var binding: ActivityMainBinding

    companion object {
        var keyPointsRestriction: List<KeyPointsRestriction>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getExerciseConstraint()

        binding.kneeSquat.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java).apply {
                putExtra("exerciseName", "Knee Squat")
            }
            startActivity(intent)
        }

        binding.halfKneeSquat.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java).apply {
                putExtra("exerciseName", "Half Squat")
            }
            startActivity(intent)
        }

        binding.reachArmsOverHear.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java).apply {
                putExtra("exerciseName", "Reach Arms Over Head")
            }
            startActivity(intent)
        }

        binding.seatedKneeExtension.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java).apply {
                putExtra("exerciseName", "Seated Knee Extension")
            }
            startActivity(intent)
        }
    }

    fun test(view: View){
        Log.d("retrofit", "hello$keyPointsRestriction")
    }

    fun getExerciseConstraint(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
            .create(ExerciseConstraintApiInterface::class.java)

        val postedData = PostedData(KeyPointsRestrictions = listOf(ExerciseData(347)), Tenant = "emma")
        val retrofitData = retrofitBuilder.getConstraint(postedData)

        Log.d("retrofit", " all data::::")

        retrofitData.enqueue(object : Callback<ExerciseConstraint> {

        override fun onResponse (
                call: Call<ExerciseConstraint>,
                response: Response<ExerciseConstraint>
            ) {
                val responseBody = response.body()
                val receivedNeededConstraint = StringBuilder()
                if (responseBody != null) {
                    for (myData in responseBody.KeyPointsRestrictions) {
//                        Log.d("retrofit", " all data:::: ${myData.Direction}")
                    }
                }
                keyPointsRestriction = responseBody?.KeyPointsRestrictions as List<KeyPointsRestriction>
                Log.d("retrofit", " all data:::: ${keyPointsRestriction!![0].MaxValidationValue}, -- ${keyPointsRestriction!![0].MinValidationValue}")
            }

            override fun onFailure(call: Call<ExerciseConstraint>, t: Throwable) {
                Log.d("retrofit", "on failure ::: " + t.message)
            }
        })
    }
}