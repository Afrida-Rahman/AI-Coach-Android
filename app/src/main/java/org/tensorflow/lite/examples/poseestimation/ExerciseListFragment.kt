package org.tensorflow.lite.examples.poseestimation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
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
        val searchExercise: SearchView = view.findViewById(R.id.search_exercise)
        displayTestId.text = displayTestId.context.getString(R.string.test_id).format(assessmentId)
        displayTestDate.text =
            displayTestDate.context.getString(R.string.test_date).format(assessmentDate)

        searchExercise.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchQuery: String): Boolean {
                if (searchQuery.isNotEmpty()) {
                    adapter.adapter = ExerciseListAdapter(
                        assessmentId,
                        assessmentDate,
                        exerciseList.filter { it.name.lowercase().startsWith(searchQuery.lowercase()) },
                        parentFragmentManager,
                        patientId,
                        tenant
                    )
                    adapter.adapter?.notifyDataSetChanged()
                }
                searchExercise.clearFocus()
                return true
            }

            override fun onQueryTextChange(searchQuery: String): Boolean {
                if (searchQuery.isNotEmpty()) {
                    adapter.adapter = ExerciseListAdapter(
                        assessmentId,
                        assessmentDate,
                        exerciseList.filter { it.name.lowercase().startsWith(searchQuery.lowercase()) },
                        parentFragmentManager,
                        patientId,
                        tenant
                    )
                    adapter.adapter?.notifyDataSetChanged()
                }
                return true
            }
        })

        searchExercise.setOnCloseListener {
            Log.d("CheckCloseListener", "I am being called")
            adapter.adapter = ExerciseListAdapter(
                assessmentId,
                assessmentDate,
                exerciseList,
                parentFragmentManager,
                patientId,
                tenant
            )
            adapter.adapter?.notifyDataSetChanged()
            searchExercise.clearFocus()
            true
        }

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