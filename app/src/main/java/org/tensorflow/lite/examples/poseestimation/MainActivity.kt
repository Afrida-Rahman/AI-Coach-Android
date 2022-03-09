package org.tensorflow.lite.examples.poseestimation

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.tensorflow.lite.examples.poseestimation.api.IExerciseService
import org.tensorflow.lite.examples.poseestimation.api.request.PatientDataPayload
import org.tensorflow.lite.examples.poseestimation.api.response.Assessment
import org.tensorflow.lite.examples.poseestimation.api.response.PatientExerciseKeypointResponse
import org.tensorflow.lite.examples.poseestimation.core.Utilities
import org.tensorflow.lite.examples.poseestimation.databinding.ActivityMainBinding
import org.tensorflow.lite.examples.poseestimation.domain.model.LogInData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var menuToggle: ActionBarDrawerToggle
    private lateinit var getPatientExerciseUrl: String
    private var assessmentListFragment: AssessmentListFragment? = null
    private var assignedAssessments: List<Assessment> = emptyList()
    private lateinit var logInData: LogInData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logInData = loadLogInData()
        binding.patientName.text =
            getString(R.string.hello_patient_name_i_m_emma).format("${logInData.firstName} ${logInData.lastName}")

        CoroutineScope(IO).launch {
            getAssignedExercises(logInData.patientId, logInData.tenant)
        }
        menuToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(menuToggle)
        menuToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        binding.menuButton.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.btnTryAgain.setOnClickListener {
            getAssignedExercises(patientId = logInData.patientId, tenant = logInData.tenant)
            it.visibility = View.GONE
            binding.progressIndicator.visibility = View.VISIBLE
        }

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.log_out_button -> {
                    saveLogInData(
                        LogInData(
                            firstName = "",
                            lastName = "",
                            patientId = "",
                            tenant = ""
                        )
                    )
                    Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SignInActivity::class.java))
                    finish()
                }
            }
            true
        }

        assessmentListFragment?.let {
            changeScreen(it)
        }
    }

    override fun onResume() {
        super.onResume()
        assessmentListFragment =
            AssessmentListFragment(assignedAssessments, logInData.patientId, logInData.tenant)
        assessmentListFragment?.let { changeScreen(it) }
    }

    override fun onBackPressed() {
        if (assessmentListFragment != null) {
            if (assessmentListFragment!!.isVisible) {
                super.onBackPressed()
                finish()
            } else {
                changeScreen(assessmentListFragment!!)
            }
        } else {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (menuToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    fun changeScreen(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            disallowAddToBackStack()
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    private fun getAssignedExercises(patientId: String, tenant: String) {
        getPatientExerciseUrl = Utilities.getUrl(loadLogInData().tenant).getPatientExerciseURL
        val client = OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        val service = Retrofit.Builder()
            .baseUrl(getPatientExerciseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IExerciseService::class.java)
        val requestPayload = PatientDataPayload(
            PatientId = patientId,
            Tenant = tenant
        )
        val response = service.getPatientExercise(requestPayload)
        response.enqueue(object : Callback<PatientExerciseKeypointResponse> {
            override fun onResponse(
                call: Call<PatientExerciseKeypointResponse>,
                response: Response<PatientExerciseKeypointResponse>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    if (responseBody.Assessments.isNotEmpty()) {
                        binding.progressIndicator.visibility = View.GONE
                        assignedAssessments = responseBody.Assessments
                        assessmentListFragment =
                            AssessmentListFragment(assignedAssessments, patientId, tenant)
                        assessmentListFragment?.let { changeScreen(it) }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "You have not performed any assessment yet. Please perform an assessment first!",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.progressIndicator.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to get assessment list from API and got empty response!",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.progressIndicator.visibility = View.GONE
                    binding.btnTryAgain.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<PatientExerciseKeypointResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Failed to get assessment list from API.",
                    Toast.LENGTH_LONG
                ).show()
                binding.progressIndicator.visibility = View.GONE
                binding.btnTryAgain.visibility = View.VISIBLE
            }
        })
    }

    private fun saveLogInData(logInData: LogInData) {
        val preferences = getSharedPreferences(
            SignInActivity.LOGIN_PREFERENCE,
            SignInActivity.PREFERENCE_MODE
        )
        preferences.edit().apply {
            putString(SignInActivity.FIRST_NAME, logInData.firstName)
            putString(SignInActivity.LAST_NAME, logInData.lastName)
            putString(SignInActivity.PATIENT_ID, logInData.patientId)
            putString(SignInActivity.TENANT, logInData.tenant)
            apply()
        }
    }

    private fun loadLogInData(): LogInData {
        val preferences = getSharedPreferences(
            SignInActivity.LOGIN_PREFERENCE,
            SignInActivity.PREFERENCE_MODE
        )
        return LogInData(
            firstName = preferences.getString(SignInActivity.FIRST_NAME, "") ?: "",
            lastName = preferences.getString(SignInActivity.LAST_NAME, "") ?: "",
            patientId = preferences.getString(SignInActivity.PATIENT_ID, "") ?: "",
            tenant = preferences.getString(SignInActivity.TENANT, "") ?: ""
        )
    }
}