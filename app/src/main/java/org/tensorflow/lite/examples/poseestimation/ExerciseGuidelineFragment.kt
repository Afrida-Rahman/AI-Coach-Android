package org.tensorflow.lite.examples.poseestimation


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import org.tensorflow.lite.examples.poseestimation.core.ImageSliderAdapter
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise


class ExerciseGuidelineFragment(
    private val testId: String,
    private val testDate: String,
    private val position: Int,
    private val exerciseList: List<HomeExercise>,
    private val patientId: String,
    private val tenant: String,
    private val active: Boolean = false
) : Fragment() {
    private lateinit var mediaSource: MediaSource
    private lateinit var exoplayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_guideline, container, false)
        val exerciseNameView: TextView = view.findViewById(R.id.exercise_name_guideline)
        val backButton: ImageButton = view.findViewById(R.id.back_button)
        val playVideo: PlayerView = view.findViewById(R.id.video_view)
        val startWorkoutButton: Button = view.findViewById(R.id.btn_start_workout_guideline)

        val exercise = exerciseList[position]
        var instruction = exercise.instruction
        val imageUrls = exercise.imageUrls
        val videoUrls = exercise.videoUrls

        exoplayer = ExoPlayer.Builder(requireContext()).build()
        playVideo.player = exoplayer
        exoplayer.setMediaSource(buildMediaSource(videoUrls))
        exoplayer.prepare()
        exoplayer.volume = 0f
        exoplayer.playWhenReady = false
        exoplayer.play()

        val gifUrl = imageUrls.find { it.endsWith(".gif") }

        backButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragment_container,
                    ExerciseListFragment(testId, testDate, patientId, tenant, exerciseList)
                )
                commit()
            }
            exoplayer.pause()
        }
        if (active) {
            startWorkoutButton.setOnClickListener {
                val intent = Intent(context, ExerciseActivity::class.java).apply {
                    putExtra(ExerciseActivity.ExerciseId, exercise.id)
                    putExtra(ExerciseActivity.TestId, testId)
                    putExtra(ExerciseActivity.Name, exercise.name)
                    putExtra(ExerciseActivity.RepetitionLimit, exercise.maxRepCount)
                    putExtra(ExerciseActivity.SetLimit, exercise.maxSetCount)
                    putExtra(ExerciseActivity.ImageUrl, gifUrl)
                    putExtra(ExerciseActivity.ProtocolId, exercise.protocolId)
                }
                view.context.startActivity(intent)
            }
        } else {
            startWorkoutButton.setOnClickListener {
                Toast.makeText(context, "Coming soon!", Toast.LENGTH_SHORT).show()
            }
        }

        exerciseNameView.text = exercise.name
        val exerciseInstructionView: TextView =
            view.findViewById(R.id.exercise_instruction_guideline)
        val htmlTagRegex = Regex("<[^>]*>|&nbsp|;")
        instruction = instruction ?: ""
        instruction = instruction.let { htmlTagRegex.replace(it, "").replace("\n", " ") }
        exerciseInstructionView.text = instruction


        if (imageUrls.isEmpty()) {
            Toast.makeText(context, "No image is available now!", Toast.LENGTH_SHORT).show()
        }

        val imageAdapter = view.findViewById<ViewPager2>(R.id.image_slide_guideline)

        imageAdapter.adapter = ImageSliderAdapter(
            view.context,
            imageUrls
        )
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        exoplayer.pause()
    }

    override fun onPause() {
        super.onPause()
        exoplayer.pause()
    }

    private fun buildMediaSource(videoURL: String): MediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoURL))

        return mediaSource
    }
}