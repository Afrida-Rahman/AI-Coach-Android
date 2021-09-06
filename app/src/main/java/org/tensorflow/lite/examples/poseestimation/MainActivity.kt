package org.tensorflow.lite.examples.poseestimation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.poseestimation.data.ExerciseData
import org.tensorflow.lite.examples.poseestimation.data.KeyPointRestrictions
import org.tensorflow.lite.examples.poseestimation.data.KeyPointsRestrictionGroup
import org.tensorflow.lite.examples.poseestimation.data.PostedData
import org.tensorflow.lite.examples.poseestimation.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private var url: String = "https://vaapi.injurycloud.com"
    private lateinit var binding: ActivityMainBinding

    companion object {
        var keyPointsRestrictionGroup: List<KeyPointsRestrictionGroup>? = null
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

    private fun getExerciseConstraint() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
            .create(ExerciseConstraintApiInterface::class.java)

        val postedData =
            PostedData(KeyPointsRestrictions = listOf(ExerciseData(347)), Tenant = "emma")
        val retrofitData = retrofitBuilder.getConstraint(postedData)

        retrofitData.enqueue(object : Callback<KeyPointRestrictions> {

            override fun onResponse(
                call: Call<KeyPointRestrictions>,
                response: Response<KeyPointRestrictions>
            ) {
                val responseBody = response.body()!!
                keyPointsRestrictionGroup = responseBody[0].KeyPointsRestrictionGroup
            }

            override fun onFailure(call: Call<KeyPointRestrictions>, t: Throwable) {
                Log.d("retrofit", "on failure ::: " + t.message)
            }
        })
    }
}