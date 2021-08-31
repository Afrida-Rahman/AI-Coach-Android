package org.tensorflow.lite.examples.poseestimation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.poseestimation.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}
