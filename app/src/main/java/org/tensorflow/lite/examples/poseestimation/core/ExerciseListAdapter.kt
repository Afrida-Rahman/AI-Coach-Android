package org.tensorflow.lite.examples.poseestimation.core

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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
            val context = this.exerciseImageView.context
            val imageUrl = if (exercise.imageUrls.isNotEmpty()) {
                exercise.imageUrls[0]
            } else {
                R.drawable.exercise
            }
            Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .thumbnail(Glide.with(context).load(R.drawable.loading).centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .override(300, 300)
                .into(this.exerciseImageView)
            exerciseNameView.text = exercise.name
            if (exercise.active) {
                exerciseStatus.setImageResource(R.drawable.ic_exercise_active)
                startExerciseButton.setOnClickListener {
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
                startExerciseButton.setOnClickListener {
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
            assignedSet.text =
                assignedSet.context.getString(R.string.assigned_set).format(exercise.maxSetCount)
            assignedRepetition.text = assignedRepetition.context.getString(R.string.assigned_repetition)
                .format(exercise.maxRepCount)
        }
    }

    override fun getItemCount(): Int = exerciseList.size

    class ExerciseItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val startExerciseButton: Button = view.findViewById(R.id.btn_start_exercise)
        val exerciseImageView: ImageView = view.findViewById(R.id.item_exercise_image)
        val exerciseNameView: TextView = view.findViewById(R.id.item_exercise_name)
        var exerciseStatus: ImageView = view.findViewById(R.id.exercise_status)
        val guidelineButton: ImageView = view.findViewById(R.id.btn_guideline)
        val assignedSet: TextView = view.findViewById(R.id.assigned_set)
        val assignedRepetition: TextView = view.findViewById(R.id.assigned_repetition)
    }
}