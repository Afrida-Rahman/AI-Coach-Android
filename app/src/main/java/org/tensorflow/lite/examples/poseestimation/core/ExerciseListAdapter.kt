package org.tensorflow.lite.examples.poseestimation.core

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.poseestimation.ExerciseActivity
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.shared.Exercises


class ExerciseListAdapter(
    private val context: Context
) : RecyclerView.Adapter<ExerciseListAdapter.ExerciseItemViewHolder>() {

    private val exerciseList = Exercises.get(context)

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
            exerciseDescription.text = exercise.description
            exerciseContainerView.setOnClickListener {
                val intent = Intent(context, ExerciseActivity::class.java).apply {
                    putExtra(ExerciseActivity.ExerciseId, exercise.id)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = exerciseList.size

    class ExerciseItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseContainerView: CardView = view.findViewById(R.id.item_exercise_container)
        val exerciseImageView: ImageView = view.findViewById(R.id.item_exercise_image)
        val exerciseNameView: TextView = view.findViewById(R.id.item_exercise_name)
        val exerciseDescription: TextView = view.findViewById(R.id.item_exercise_description)
    }
}