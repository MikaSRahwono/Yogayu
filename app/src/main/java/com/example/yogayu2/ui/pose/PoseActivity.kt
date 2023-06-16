package com.example.yogayu2.ui.pose

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.response.DetailPoseResponse
import com.example.yogayu2.databinding.ActivityPoseBinding
import com.example.yogayu2.factory.ViewModelFactory
import com.example.yogayu2.ui.camera.CameraActivity
import com.example.yogayu2.ui.level.LevelActivity
import com.example.yogayu2.ui.level.LevelViewModel
import com.example.yogayu2.ui.preview.PreviewActivity
import com.example.yogayu2.ui.preview.rotateFile
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class PoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPoseBinding
    private lateinit var poseViewModel: PoseViewModel

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PoseActivity.REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        val id = intent.getIntExtra("id", 0)
        val pref = TokenPreferences.getInstance(dataStore)
        poseViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            PoseViewModel(pref)::class.java
        )

        poseViewModel.getToken().observe(this) {token ->
            if (token.isNotEmpty()) {
                poseViewModel.getDetailPose(token, id)
                poseViewModel.detailPose.observe(this) {detailPose ->
                    setDetailContent(detailPose)
                    binding.btBack.setOnClickListener { view ->
                        if (view.id == binding.btBack.id) {
                            val intent = Intent(this@PoseActivity, LevelActivity::class.java)
                            intent.putExtra("id", detailPose.yogaLevel.id)
                            startActivity(intent)
                        }
                    }
                }
            }
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    fun setDetailContent(detailPose: DetailPoseResponse) {
        binding.tvPoseName.text = detailPose.name
        Glide.with(this)
            .load(detailPose.imageUrl)
            .into(binding.ivPoseImage)
        binding.tvDesc.text = detailPose.description
        binding.tvPoint.text = "${detailPose.firstRewardPoints} points"
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == PreviewActivity.CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let { file ->
                rotateFile(file, isBackCamera)
//                getFile = file
//                binding.previewImage.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }
}