package org.tensorflow.lite.examples.poseestimation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import org.tensorflow.lite.examples.poseestimation.core.ExerciseGuidelineImageListAdapter
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise


class ExerciseGuidelineFragment(
    private val testId: String,
    private val testDate: String,
    private val position: Int,
    private val exerciseList: List<HomeExercise>,
    private val patientId: String,
    private val tenant: String
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

        backButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragment_container,
                    ExerciseListFragment(testId, testDate, exerciseList, patientId, tenant)
                )
                commit()
            }
            exoplayer.pause()
        }

        exerciseNameView.text = exercise.name
        val exerciseInstructionView: TextView =
            view.findViewById(R.id.exercise_instruction_guideline)
        val htmlTagRegex = Regex("<[^>]*>|&nbsp|;")
        instruction = instruction ?: ""
        instruction = instruction.let { htmlTagRegex.replace(it, "").replace("\n", " ") }
        exerciseInstructionView.text = instruction

        val adapter = view.findViewById<RecyclerView>(R.id.exercise_guideline_image_list_container)
        if (imageUrls.isEmpty()) {
            Toast.makeText(context, "No image is available now!", Toast.LENGTH_SHORT).show()
        }
        adapter.adapter = ExerciseGuidelineImageListAdapter(view.context, imageUrls)
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