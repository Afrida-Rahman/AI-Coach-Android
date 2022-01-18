package org.tensorflow.lite.examples.poseestimation

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_guideline, container, false)
        val exerciseNameView: TextView = view.findViewById(R.id.exercise_name_guideline)
        val backButton: ImageButton = view.findViewById(R.id.back_button)
        val videoView: VideoView = view.findViewById(R.id.video_view)
        val playVideo: ImageView = view.findViewById(R.id.play_video)

        val exercise = exerciseList[position]
        var instruction = exercise.instruction
        val imageUrls = exercise.imageUrls
        val videoUrls = exercise.videoUrls

        val mediaController = MediaController(view.context)
        playVideo.setOnClickListener {
            mediaController.setAnchorView(videoView)
            val pd = ProgressDialog(view.context)
            pd.setMessage("Loading...")
            pd.show()

            val uri: Uri = Uri.parse(videoUrls)
            videoView.setMediaController(mediaController)
            videoView.setVideoURI(uri)
            videoView.requestFocus()

            videoView.setOnPreparedListener {//close the progress dialog when buffering is done
                videoView.start()
                pd.dismiss()
                playVideo.visibility = View.GONE
            }
            playVideo.visibility = View.VISIBLE
        }

        backButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragment_container,
                    ExerciseListFragment(testId, testDate, exerciseList, patientId, tenant)
                )
                commit()
            }
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
}