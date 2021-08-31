package org.tensorflow.lite.examples.poseestimation.core

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.exercise.Exercise

class ExerciseListAdapter(private val exerciseList: List<Exercise>) :
    RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder>() {

    override fun onBindViewHolder(holder: ExerciseListViewHolder, position: Int) {
        val currentItem = exerciseList[position]
        holder.apply {
            image.setImageResource(currentItem.image)
            exerciseName.text = currentItem.name
            exerciseDescription.text = currentItem.description
        }
    }

    override fun getItemCount() = exerciseList.size

    class ExerciseListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.exercise_image)
        val exerciseName: TextView = itemView.findViewById(R.id.exercise_name)
        val exerciseDescription: TextView = itemView.findViewById(R.id.exercise_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewHolder {
        Log.d("OrangeTree", "Debug line 1")
        val inflater = LayoutInflater.from(parent.context)
        Log.d("OrangeTree", "Debug line 2")
        val view = inflater.inflate(R.layout.item_exercise, parent, false)
        Log.d("OrangeTree", "Debug line 3")
        return ExerciseListViewHolder(view)
    }
}