package org.tensorflow.lite.examples.poseestimation.core

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.poseestimation.ExerciseActivity
import org.tensorflow.lite.examples.poseestimation.ExerciseGuidelineFragment
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.exercise.IExercise

class ExerciseListAdapter(
    private val testId: String,
    private val exerciseList: List<IExercise>,
    private val manager: FragmentManager
) : RecyclerView.Adapter<ExerciseListAdapter.ExerciseItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseItemViewHolder {
        return ExerciseItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_exercise, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ExerciseItemViewHolder, position: Int) {
        val exercise = exerciseList[position]
        holder.apply {
            exerciseImageView.setImageResource(exercise.imageResourceId)
            exerciseNameView.text = exercise.name
            if (exercise.active) {
                exerciseStatus.setImageResource(R.drawable.ic_exercise_active)
                exerciseContainerView.setOnClickListener {
                    val intent = Intent(it.context, ExerciseActivity::class.java).apply {
                        putExtra(ExerciseActivity.ExerciseId, exercise.id)
                        putExtra(ExerciseActivity.TestId, testId)
                        putExtra(ExerciseActivity.Name, exercise.name)
                        putExtra(ExerciseActivity.RepetitionLimit, exercise.maxRepCount)
                        putExtra(ExerciseActivity.SetLimit, exercise.maxSetCount)
                        putExtra(ExerciseActivity.ProtocolId, exercise.protocolId)
                    }
                    it.context.startActivity(intent)
                }
            } else {
                exerciseStatus.setImageResource(R.drawable.ic_exercise_inactive)
                exerciseContainerView.setOnClickListener {
                    Toast.makeText(it.context, "Coming soon", Toast.LENGTH_LONG).show()
                }
            }
            guidelineButton.setOnClickListener {
                manager.beginTransaction().apply {
                    replace(
                        R.id.fragment_container,
                        ExerciseGuidelineFragment(
                            testId = testId,
                            position = position,
                            exerciseList = exerciseList
                        )
                    )
                    commit()
                }
            }
            setRepDisplay.text = setRepDisplay.context.getString(R.string.sets_repetitions_set).format(exercise.maxSetCount, exercise.maxRepCount)
        }
    }

    override fun getItemCount(): Int = exerciseList.size

    class ExerciseItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseContainerView: CardView = view.findViewById(R.id.item_exercise_container)
        val exerciseImageView: ImageView = view.findViewById(R.id.item_exercise_image)
        val exerciseNameView: TextView = view.findViewById(R.id.item_exercise_name)
        var exerciseStatus: ImageView = view.findViewById(R.id.exercise_status)
        val guidelineButton: ImageView = view.findViewById(R.id.btn_guideline)
        val setRepDisplay: TextView = view.findViewById(R.id.set_rep_display)
    }
}