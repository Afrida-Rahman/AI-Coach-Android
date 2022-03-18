package org.tensorflow.lite.examples.poseestimation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.poseestimation.api.response.Assessment
import org.tensorflow.lite.examples.poseestimation.core.AssessmentListAdapter
import org.tensorflow.lite.examples.poseestimation.domain.model.TestId

class AssessmentListFragment(
    private val assessments: List<Assessment>,
    private val patientId: String,
    private val tenant: String,
    private val width: Int = 0
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_assessment_list, container, false)
        val adapter = view.findViewById<RecyclerView>(R.id.assessment_list_container)
        val searchAssessment: SearchView = view.findViewById(R.id.search_assessment)
        val testList = mutableListOf<TestId>()
        assessments.forEach { assessment ->
            testList.add(
                TestId(
                    id = assessment.TestId,
                    bodyRegionId = assessment.BodyRegionId,
                    bodyRegionName = assessment.BodyRegionName,
                    providerName = assessment.ProviderName,
                    providerId = assessment.ProviderId,
                    testDate = assessment.CreatedOnUtc.split("T")[0],
                    isReportReady = assessment.IsReportReady,
                    registrationType = assessment.RegistrationType,
                    totalExercises = assessment.TotalExercise
                )
            )
        }
        searchAssessment.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchQuery: String): Boolean {
                if (searchQuery.isNotEmpty()) {
                    adapter.adapter = AssessmentListAdapter(
                        testList.filter { it.id.lowercase().contains(searchQuery.lowercase()) },
                        parentFragmentManager,
                        patientId,
                        tenant
                    )
                    adapter.adapter?.notifyDataSetChanged()
                }
                searchAssessment.clearFocus()
                return true
            }

            override fun onQueryTextChange(searchQuery: String): Boolean {
                if (searchQuery.isNotEmpty()) {
                    adapter.adapter = AssessmentListAdapter(
                        testList.filter { it.id.lowercase().contains(searchQuery.lowercase()) },
                        parentFragmentManager,
                        patientId,
                        tenant
                    )
                    adapter.adapter?.notifyDataSetChanged()
                }
                return true
            }
        })

        searchAssessment.setOnCloseListener {
            adapter.adapter = AssessmentListAdapter(
                testList,
                parentFragmentManager,
                patientId,
                tenant
            )
            adapter.adapter?.notifyDataSetChanged()
            searchAssessment.clearFocus()
            true
        }
        Log.d("WidthOfScreen", "$width")
        if (width > 1300) {
            adapter.layoutManager = GridLayoutManager(context, 2)
        }
        adapter.adapter = AssessmentListAdapter(
            testList,
            parentFragmentManager,
            patientId,
            tenant
        )
        return view
    }

}