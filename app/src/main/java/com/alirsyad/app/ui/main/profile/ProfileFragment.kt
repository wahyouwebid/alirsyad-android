package com.alirsyad.app.ui.main.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.profile.DataProfile
import com.alirsyad.app.data.model.profile.upload.ResponseUploadPhoto
import com.alirsyad.app.data.state.ProfileState
import com.alirsyad.app.data.state.UploadPhotoState
import com.alirsyad.app.databinding.FragmentProfileBinding
import com.alirsyad.app.ui.MainViewModel
import com.alirsyad.app.ui.login.LoginActivity
import com.alirsyad.app.ui.personalinformation.PersonalInformationActivity
import com.alirsyad.app.ui.privacyandpolicy.PrivacyAndPolicyActivity
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.FileUtils
import com.alirsyad.app.utils.PermissionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment(val activity: Activity) : Fragment() {

    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()
    private var uri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupListener()
        setupToolbar()
        setupObserveViewModel()
        setupPhotoProfile()
        setupData()
    }

    private fun setupToolbar() {
        with(binding) {
            toolbar.setToolbar(getString(R.string.title_profile))
        }
    }

    private fun setupData() = with(binding) {
        when(viewModel.getIsPengunjung()) {
            0 -> tvPersonalInformationSub.text = getString(R.string.title_personal_information_sub)
            else -> tvPersonalInformationSub.text = getString(R.string.title_personal_information_sub_visitor)
        }
    }

    private fun setupViewModel() {
        viewModel.getProfile()
    }

    private fun setupObserveViewModel() {
        viewModel.profile.observe(viewLifecycleOwner) {
            when (it) {
                is ProfileState.Loading -> {}
                is ProfileState.Result -> {
                    setData(it.data.data)
                }
                is ProfileState.Error -> {
                    setupErrorGetPhoto(it.error)
                }
            }
        }

        viewModel.uploadPhoto.observe(viewLifecycleOwner) { upload ->
            when (upload) {
                is UploadPhotoState.Loading -> {
                    setupLoading(true)
                }
                is UploadPhotoState.Result -> {
                    setupLoading(false)
                    setUploadResult(upload.data)
                }
                is UploadPhotoState.Error -> {
                    setupLoading(false)
                    setupError(upload.error)
                }
            }
        }
    }

    private fun setData(data : DataProfile) = with(binding) {
        ivProfile.load(data.photo) {
            error(R.drawable.ic_profile_default)
        }
    }

    private fun setupPhotoProfile() = with(binding) {
        val photo = viewModel.getPhoto()
        if (photo.isNotEmpty()) {
            ivProfile.load(photo) {
                error(R.drawable.ic_profile_default)
            }
        }
    }

    private fun setUploadResult(data: ResponseUploadPhoto) = with(binding){
        viewModel.updatePhoto(data.data.imagePath)
        viewModel.updateSessionPhoto(data.data.imageUrl)
        ivProfile.load(data.data.imageUrl) {
            error(R.drawable.ic_profile_default)
        }
    }

    private fun setupListener() = with(binding){
        clPersonalInformation.setOnClickListener {
            startActivity(Intent(requireContext(), PersonalInformationActivity::class.java))
        }

        clPrivacyAndPolicy.setOnClickListener {
            startActivity(Intent(requireContext(), PrivacyAndPolicyActivity::class.java))
        }

        ivEditProfile.setOnClickListener {
            checkPermission()
        }

        btnLogout.setOnClickListener {
            DibBottomSheet(ErrorType.LOGOUT.name, requireContext(), onClickCancel = {
                viewModel.logOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                activity.finish()
            }).show(childFragmentManager, ErrorType.LOGOUT.name)
        }
    }

    private fun checkPermission() {
        if (PermissionManager.checkPermission(activity)) {
            intent.launch(Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            })
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
     val intent: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                Log.e("debug", "debug: $it")
                uri = it.data!!.data
                viewModel.uploadPhoto(FileUtils.getFile(requireContext(), it.data!!.data)!!)
            }
        }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                intent.launch(Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                })
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }


    private fun setupLoading(isLoading: Boolean) = with(binding) {
        loading.setLoading(isLoading)
    }

    private fun setupErrorGetPhoto(error: Throwable) {
        ErrorUtils.setupError(this, error,
            showBottomSheet = {
                DibBottomSheet(
                    ErrorType.ERROR_PROCESS_DATA.name,
                    requireContext(),
                ) {
                    setupViewModel()
                }.show(childFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                setupViewModel()
            }
        )
    }

    private fun setupError(error: Throwable) {
        ErrorUtils.setupError(this, error,
            showBottomSheet = {
                DibBottomSheet(
                    ErrorType.ERROR_PROCESS_DATA.name,
                    requireContext(),
                    getString(R.string.title_errpr_upload_photo_max_size)
                ).show(childFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                setupViewModel()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}