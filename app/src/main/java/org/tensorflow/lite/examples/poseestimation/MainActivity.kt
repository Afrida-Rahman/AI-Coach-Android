package org.tensorflow.lite.examples.poseestimation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.poseestimation.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private var url: String = "https://vaapi.injurycloud.com/api/exercisekeypoint/"
    private lateinit var tv: TextView

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tv = findViewById(R.id.tv_retrofit_data)
        tv.text = ""
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

        val postedData = PostedData(Tetant = "emma")
        val retrofitData = retrofitBuilder.getConstraint(postedData)


        retrofitData.enqueue(object : Callback<List<KeyPointsRestriction>?> {
            override fun onResponse(
                call: Call<List<KeyPointsRestriction>?>,
                response: Response<List<KeyPointsRestriction>?>
            ) {
                val responseBody = response.body()
                val myStringBuilder = StringBuilder()
                if (responseBody != null) {
                    for (myData in responseBody) {
                        myStringBuilder.append(myData.AngleArea)
                        myStringBuilder.append("\n")
                        myStringBuilder.append(myData.CapturedImage)
                        myStringBuilder.append("\n\n")
                    }
                    tv.text = myStringBuilder
                }
            }

            override fun onFailure(call: Call<List<KeyPointsRestriction>?>, t: Throwable) {
                Log.d("MainActivity", "on failure ::: " + t.message)
            }
        })
    }
}