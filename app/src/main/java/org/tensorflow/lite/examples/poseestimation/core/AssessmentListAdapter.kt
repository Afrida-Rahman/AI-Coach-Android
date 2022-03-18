package org.tensorflow.lite.examples.poseestimation.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.poseestimation.ExerciseListFragment
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.domain.model.TestId

class AssessmentListAdapter(
    private val testList: List<TestId>,
    private val manager: FragmentManager,
    private val patientId: String,
    private val tenant: String
) : RecyclerView.Adapter<AssessmentListAdapter.AssessmentItemViewHolder>() {

    class AssessmentItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val testId: TextView = view.findViewById(R.id.test_id)
        val testDate: TextView = view.findViewById(R.id.test_date)
        val reportReadyIcon: ImageView = view.findViewById(R.id.report_ready_icon)
        val providerName: TextView = view.findViewById(R.id.provider_name)
        val bodyRegion: TextView = view.findViewById(R.id.body_region)
        val registrationType: TextView = view.findViewById(R.id.registration_type)
        val exerciseCount: TextView = view.findViewById(R.id.exercise_count)
        val goToExerciseList: Button = view.findViewById(R.id.go_to_exercise_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssessmentItemViewHolder {
        return AssessmentItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_assessment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AssessmentItemViewHolder, position: Int) {
        val item = testList[position]
        holder.apply {
            if (item.isReportReady)
                reportReadyIcon.setImageResource(R.drawable.ic_check)
            else
                reportReadyIcon.setImageResource(R.drawable.ic_cross)
            testId.context.apply {
                testId.text = getString(R.string.test_id).format(item.id)
                testDate.text = getString(R.string.test_date).format(item.testDate)
                providerName.text =
                    getString(R.string.provider_name_value).format(item.providerName ?: "Unknown")
                bodyRegion.text = getString(R.string.body_region_value).format(item.bodyRegionName)
                registrationType.text =
                    getString(R.string.registration_type_value).format(item.registrationType)
                exerciseCount.text = getString(R.string.exercise_count).format(item.totalExercises)
                if (item.totalExercises <= 0) {
                    goToExerciseList.isEnabled = false
                } else {

                    goToExerciseList.isEnabled = true
                    goToExerciseList.setOnClickListener {
                        manager.beginTransaction().apply {
                            replace(
                                R.id.fragment_container,
                                ExerciseListFragment(
                                    assessmentId = item.id,
                                    assessmentDate = item.testDate,
                                    patientId = patientId,
                                    tenant = tenant
                                )
                            )
                            commit()
                        }
                    }

                }
            }
        }
    }

    override fun getItemCount() = testList.size
}