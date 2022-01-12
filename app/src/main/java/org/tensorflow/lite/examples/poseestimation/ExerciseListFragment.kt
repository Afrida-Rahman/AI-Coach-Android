package org.tensorflow.lite.examples.poseestimation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.poseestimation.core.ExerciseListAdapter
import org.tensorflow.lite.examples.poseestimation.exercise.home.HomeExercise

class ExerciseListFragment(
    private val assessmentId: String,
    private val assessmentDate: String,
    private val exerciseList: List<HomeExercise>,
    private val patientId: String,
    private val tenant: String,
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_list, container, false)
        val adapter = view.findViewById<RecyclerView>(R.id.exercise_list_container)
        val displayTestId: TextView = view.findViewById(R.id.test_id_display)
        val displayTestDate: TextView = view.findViewById(R.id.test_date_display)
        displayTestId.text = displayTestId.context.getString(R.string.test_id).format(assessmentId)
        displayTestDate.text =
            displayTestDate.context.getString(R.string.test_date).format(assessmentDate)
        adapter.adapter = ExerciseListAdapter(
            assessmentId,
            assessmentDate,
            exerciseList,
            parentFragmentManager,
            patientId,
            tenant
        )
        return view
    }
}