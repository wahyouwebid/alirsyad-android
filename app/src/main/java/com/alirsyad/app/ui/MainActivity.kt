package com.alirsyad.app.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.databinding.ActivityMainBinding
import com.alirsyad.app.ui.main.achieve.AchieveFragment
import com.alirsyad.app.ui.main.ereport.EReportFragment
import com.alirsyad.app.ui.main.home.HomeFragment
import com.alirsyad.app.ui.main.profile.ProfileFragment
import com.alirsyad.app.ui.main.subject.SubjectFragment
import com.alirsyad.app.utils.Constant
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    private val mOnNavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener { menuItem ->
        setUpNavigation(menuItem.itemId)
            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupComponent()
        setupStatusBar(this)
        setupFragment(HomeFragment { setSubjectMore() })
        setupNavigation()
    }

    private fun setupComponent() = with(binding){
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        // Manual navigation for tablet landscape
        menuHome.setOnClickListener {
            setupFragment(HomeFragment{ setSubjectMore() })
            setActiveMenu(R.id.menu_home)
        }
        menuSubject.setOnClickListener {
            setupFragment(SubjectFragment())
            setActiveMenu(R.id.menu_subject)
        }
        menuAchieve.setOnClickListener {
            checkIsVisitor(AchieveFragment())
            setActiveMenu(R.id.menu_achieve)
        }
        menuProfile.setOnClickListener {
            setupFragment(ProfileFragment(this@MainActivity), true)
            setActiveMenu(R.id.menu_profile)
        }
    }

    private fun setUpNavigation(itemId: Int) {
        when (itemId) {
            R.id.menu_home -> setupFragment(HomeFragment{ setSubjectMore() })
            R.id.menu_report -> checkIsVisitor(EReportFragment())
            R.id.menu_subject -> setupFragment(SubjectFragment())
            R.id.menu_achieve -> checkIsVisitor(AchieveFragment())
            R.id.menu_profile -> setupFragment(ProfileFragment(this), true)
        }
    }

    private fun checkIsVisitor(fragment: Fragment,) {
        if (viewModel.getIsPengunjung() == Constant.Role.STUDENT_INT) {
            setupFragment(fragment)
        } else {
            DibBottomSheet(ErrorType.ERROR_IS_VISITOR.name, this)
                .show(supportFragmentManager, ErrorType.ERROR_IS_VISITOR.name)
        }
    }

    private fun setSubjectMore() = with(binding) {
        setupFragment(SubjectFragment())
    }

    private fun setupNavigation() = with(binding) {
        val radius = resources.getDimension(R.dimen.rounded_bottombar)
        val bottomNavigationViewBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomNavigationViewBackground.shapeAppearanceModel =
            bottomNavigationViewBackground.shapeAppearanceModel.toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                .build()
        fabReport.setOnClickListener { setupFragment(EReportFragment()) }
    }

    private fun setupFragment(fragment: Fragment, isProfile: Boolean = false) = with(binding){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_layout, fragment)
        transaction.commit()
        setProfile(isProfile)
    }

    private fun setProfile(isProfile: Boolean) = with(binding) {
        if (isProfile) {
            ivProfile.background = ContextCompat.getDrawable(applicationContext, R.drawable.ic_profile_selected)
            tvProfile.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
        } else {
            ivProfile.background = ContextCompat.getDrawable(applicationContext, R.drawable.ic_profile)
            tvProfile.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimaryLighter))
        }
    }

    private fun setActiveMenu(menuId: Int) = with(binding) {
        val colorPrimary = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
        val colorInactive = ContextCompat.getColor(applicationContext, R.color.colorPrimaryLighter)

        // Reset semua menu ke state inactive
        ivHome.setColorFilter(colorInactive)
        tvHome.setTextColor(colorInactive)

        ivSubject.setColorFilter(colorInactive)
        tvSubjectMenu.setTextColor(colorInactive)

        ivAchieve.setColorFilter(colorInactive)
        tvAchieve.setTextColor(colorInactive)

        ivProfile.background = ContextCompat.getDrawable(applicationContext, R.drawable.ic_profile)
        tvProfile.setTextColor(colorInactive)

        // Set menu yang aktif
        when (menuId) {
            R.id.menu_home -> {
                ivHome.setColorFilter(colorPrimary)
                tvHome.setTextColor(colorPrimary)
            }
            R.id.menu_subject -> {
                ivSubject.setColorFilter(colorPrimary)
                tvSubjectMenu.setTextColor(colorPrimary)
            }
            R.id.menu_achieve -> {
                ivAchieve.setColorFilter(colorPrimary)
                tvAchieve.setTextColor(colorPrimary)
            }
            R.id.menu_profile -> {
                ivProfile.background = ContextCompat.getDrawable(applicationContext, R.drawable.ic_profile_selected)
                tvProfile.setTextColor(colorPrimary)
            }
            else -> {
                // Do nothing for other menu items
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.e("debug", "debug: $grantResults")
        if (requestCode == 400) {
            if (grantResults.isNotEmpty()) {
                val read = grantResults[0] == PackageManager.PERMISSION_GRANTED
                Log.e("debug", "debug: ${grantResults[0]}")
                if (read) {
                    Log.i("debug", "onRequestPermissionsResult: Permission Granted")
                }
            }
        }
    }
}