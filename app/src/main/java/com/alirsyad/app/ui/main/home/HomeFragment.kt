package com.alirsyad.app.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.banner.DataBanner
import com.alirsyad.app.data.model.courses.DataCourse
import com.alirsyad.app.data.model.courses.Tingkat
import com.alirsyad.app.data.model.ereport.EReportAchievement
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.model.toolbar.DataToolbar
import com.alirsyad.app.data.model.update.DataUpdate
import com.alirsyad.app.data.state.BannerState
import com.alirsyad.app.data.state.CourseState
import com.alirsyad.app.data.state.EReportAchievementState
import com.alirsyad.app.data.state.TingkatState
import com.alirsyad.app.data.state.UpdateState
import com.alirsyad.app.databinding.FragmentHomeBinding
import com.alirsyad.app.ui.MainViewModel
import com.alirsyad.app.ui.adapter.BannerAdapter
import com.alirsyad.app.ui.adapter.NewUpdateCarouselAdapter
import com.alirsyad.app.ui.adapter.SubjectClassAdapter
import com.alirsyad.app.ui.adapter.SubjectClassTKAdapter
import com.alirsyad.app.ui.adapter.SubjectCourseAdapter
import com.alirsyad.app.ui.learning.learningmodule.detail.DetailLearningModuleActivity
import com.alirsyad.app.ui.learning.learningvideo.detail.DetailLearningVideoActivity
import com.alirsyad.app.ui.subjectsummary.SubjectSummaryActivity
import com.alirsyad.app.ui.updatesubject.UpdateSubjectActivity
import com.alirsyad.app.utils.Constant.EMPTY_STRING
import com.alirsyad.app.utils.Constant.Intent.DATA
import com.alirsyad.app.utils.Constant.Intent.ID
import com.alirsyad.app.utils.Constant.Intent.NAME
import com.alirsyad.app.utils.Constant.VisitorStatus.AKTIF
import com.alirsyad.app.utils.Constant.VisitorStatus.BELUM_DIKONFIRMASI
import com.alirsyad.app.utils.Constant.VisitorStatus.TIDAK_AKTIF
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class HomeFragment(
    showMore: () -> Unit
) : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    private val bannerAdapter: BannerAdapter by lazy {
        BannerAdapter()
    }

    private var timer: Timer? = null
    private var currentPage: Int = 0
    private val dataBannerList: MutableList<DataBanner> = arrayListOf()

    private val newUpdateAdapter: NewUpdateCarouselAdapter by lazy {
        NewUpdateCarouselAdapter { idCourse, nameCourse, idModule, nameModule, trigger ->
            if (trigger == "video") {
                detailVideo(idCourse, nameCourse, idModule, nameModule)
            } else {
                detailModule(idCourse, nameCourse, idModule, nameModule)
            }
        }
    }

    private val subjectClassAdapter: SubjectClassAdapter by lazy {
        SubjectClassAdapter(viewModel) { item -> getMapelByClass(item) }
    }

    private val subjectClassTKAdapter: SubjectClassTKAdapter by lazy {
        SubjectClassTKAdapter(viewModel) { item -> getMapelByClass(item) }
    }

    private val subjectCourseAdapter: SubjectCourseAdapter by lazy {
        SubjectCourseAdapter (
            isHome = true,
            showDetail = { item -> detailCourse(item) },
            showMore = { showMore.invoke() }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        setupRefresh()
    }

    private fun setup() {
        setupListener()
        setupAdapter()
        setupViewModel()
        setupObserveViewModel()
        setToolbar()
    }

    private fun setToolbar() {
        with(binding) {
            toolbar.setToolbar(
                viewModel.getName(),
                viewModel.getPhoto(),
                DataToolbar(
                    viewModel.getIsPengunjung(),
                    viewModel.getTingkat(),
                    viewModel.getKelas()
                )
            )
        }
    }

    private fun setupListener() {

        with(binding) {
            tvViewMoreUpdate.setOnClickListener {
                startActivity(Intent(context, UpdateSubjectActivity::class.java))
            }
            ivTooltips.setOnClickListener {
                val tooltip = SimpleTooltip.Builder(requireContext())
                    .anchorView(ivTooltips)
                    .text(getString(R.string.title_tooltips_achievment))
                    .gravity(Gravity.TOP)
                    .modal(true)
                    .showArrow(false)
                    .animated(true)
                    .transparentOverlay(true)
                    .animationDuration(2000)
                    .contentView(R.layout.layout_tooltips,R.id.tv_tooltips)
                    .focusable(true)
                    .build()
                if (tooltip.isShowing) {
                    tooltip.dismiss()
                } else {
                    tooltip.show()
                }
            }
        }
    }

    private fun setupAdapter() {
        with(binding) {
            vpBanner.adapter = bannerAdapter
            dotsBanner.setViewPager2(vpBanner)
            rvSubjectCourse.also {
                it.isNestedScrollingEnabled = false
                it.adapter = subjectCourseAdapter
                it.layoutManager = GridLayoutManager(context, 4)
            }

            rvSubjectUpdate.also {
                it.adapter = newUpdateAdapter
                it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            rvSubjectClass.also {
                it.adapter = getAdapter()
                it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun getAdapter() : RecyclerView.Adapter<out RecyclerView.ViewHolder> {
        return if (Utils.isNumeric(viewModel.getTingkat()) == true) {
            subjectClassAdapter
        } else {
            subjectClassTKAdapter
        }
    }

    private fun setupAutoSlider() = with(binding) {
        val update = Runnable {
            if (currentPage == dataBannerList.size) {
                currentPage = 0
            }
            vpBanner.setCurrentItem( currentPage ++, true)
        }
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post(update)
            }
        }, 500, 5000)
    }

    private fun setupViewModel() {
        viewModel.getBanner()
        viewModel.getClass()
        viewModel.getNewUpdate()
        viewModel.getYourAchievement()
    }

    private fun setupObserveViewModel() {
        viewModel.banner.observe(viewLifecycleOwner) {
            when (it) {
                is BannerState.Loading -> {
                    setupLoadingBanner(true)
                }
                is BannerState.Result -> {
                    setupLoadingBanner(false)
                    setDataBanner(it.data.data)
                }
                is BannerState.Error -> {
                    setupError(it.error)
                }
            }
        }

        viewModel.newUpdate.observe(viewLifecycleOwner) {
            when (it) {
                is UpdateState.Loading -> {
                    setupLoadingClassUpdate(true)
                }
                is UpdateState.Result -> {
                    setupLoadingClassUpdate(false)
                    setDataUpdate(it.data.data)
                }
                is UpdateState.Error -> {
                    setupError(it.error)
                }
            }
        }

        viewModel.subjectClass.observe(viewLifecycleOwner) {
            when (it) {
                is TingkatState.Loading -> {
                    setupLoadingSubjectClass(true)
                }
                is TingkatState.Result -> {
                    setupLoadingSubjectClass(false)
                    setDataSubjectClass(it.data.data)
                }
                is TingkatState.Error -> {
                    setupError(it.error)
                }
            }
        }

        viewModel.subjectCourse.observe(viewLifecycleOwner) {
            when (it) {
                is CourseState.Loading -> {
                    setupLoadingSubjectCourse(true)
                }
                is CourseState.Result -> {
                    setupLoadingSubjectCourse(false)
                    setDataSubjectCourse(it.data.data)
                }
                is CourseState.Error -> {
                    setupError(it.error)
                }
            }
        }

        viewModel.yourAchievement.observe(viewLifecycleOwner) {
            when (it) {
                is EReportAchievementState.Loading -> {}
                is EReportAchievementState.Result -> {
                    setDataYourAchievement(it.data.data)
                }
                is EReportAchievementState.Error -> {}
            }
        }
    }

    private fun setDataBanner(data: List<DataBanner>) {
        when {
            data.isEmpty() -> {
                setupIsEmptyBanner()
            }
            else -> {
                dataBannerList.clear()
                dataBannerList.addAll(data)
                bannerAdapter.setData(data)
                setupAutoSlider()
            }
        }
    }

    private fun setDataYourAchievement(data: EReportAchievement?) = with(binding) {
        if (data != null) {
            tvResultValue.text = String.format("${data.totalBenar}/${data.totalTerjawab}")
            pbProgress?.progress = data.percentage!!
        } else {
            tvResultValue.text = String.format("0/0")
            pbProgress?.progress = 0F
        }
    }

    private fun setDataSubjectCourse(data: List<DataCourse>) {
        when {
            data.isEmpty() -> {
                setupIsEmptySubjectMapel()
            }
            else -> {
                subjectCourseAdapter.setData(data.take(8))
            }
        }
    }

    private fun setDataUpdate(data: List<DataUpdate>) = with(binding){
        if(viewModel.getStatus() == AKTIF) {
            cvVisitorActive?.hide(true)
            rvSubjectUpdate.show()
            if (data.size >= 5) {
                tvViewMoreUpdate.show()
                newUpdateAdapter.setData(data.take(5))
            } else {
                tvViewMoreUpdate.hide()
                newUpdateAdapter.setData(data)
            }
        } else {
            tvViewMoreUpdate.hide(true)
            cvVisitorActive?.show()
            rvSubjectUpdate.hide(true)
        }
    }

    private fun setDataSubjectClass(data: List<Tingkat>) {
        when {
            data.isEmpty() -> {
                setupIsEmptySubjectClass()
            }
            else -> {
                if (Utils.isNumeric(viewModel.getTingkat()) == true) {
                    subjectClassAdapter.setData(data)
                } else {
                    subjectClassTKAdapter.setData(data)
                }

                val isTab = viewModel.getIsTab()
                if (isTab) {
                    if (viewModel.getClassPosition() == 0) {
                        viewModel.getMapelByClass(data[0].id)
                    } else {
                        viewModel.getMapelByClass(viewModel.getClassId())
                        binding.rvSubjectClass.scrollToPosition(viewModel.getClassId() - 1)
                    }
                } else {
                    setSubjectByClass(data)
                }
            }
        }
    }

    private fun setSubjectByClass(data: List<Tingkat>) {
        if (viewModel.getTingkatId() != 0) {
            when {
                viewModel.getTingkatId() in 7..9 -> {
                    val classId = viewModel.getTingkatId()
                    val dataFiltered = data.filter { it.id == classId }
                    val position = dataFiltered[0].id - 7
                    viewModel.getMapelByClass(classId)
                    viewModel.setClassPosition(position)
                    binding.rvSubjectClass.scrollToPosition(position)
                }

                viewModel.getTingkatId() > 9 -> {
                    if (viewModel.getTingkatId() >= 12) {
                        val classId = viewModel.getTingkatId()
                        val dataFiltered = data.filter { it.id == classId }
                        val position = dataFiltered[0].id - 11
                        viewModel.getMapelByClass(classId)
                        viewModel.setClassPosition(position)
                        binding.rvSubjectClass.scrollToPosition(position)
                    } else {
                        val classId = viewModel.getTingkatId()
                        val dataFiltered = data.filter { it.id == classId }
                        val position = dataFiltered[0].id - 10
                        viewModel.getMapelByClass(classId)
                        viewModel.setClassPosition(position)
                        binding.rvSubjectClass.scrollToPosition(position)
                    }
                }

                else -> {
                    val classId = viewModel.getTingkatId()
                    val dataFiltered = data.filter { it.id == classId }
                    val position = dataFiltered[0].id - 1
                    viewModel.getMapelByClass(classId)
                    viewModel.setClassPosition(position)
                    binding.rvSubjectClass.scrollToPosition(position)
                }
            }
        } else {
            val position = data[0].id
            viewModel.getMapelByClass(position)
            viewModel.setClassPosition(0)
            binding.rvSubjectClass.scrollToPosition(0)
        }
    }

    private fun setupLoadingBanner(loading: Boolean) {
        with(binding) {
            if (loading) {
                dotsBanner.hide()
                vpBanner.hide()
                shHomeCarousel.show()
                shHomeCarousel.startShimmer()
            } else {
                dotsBanner.show()
                vpBanner.show()
                shHomeCarousel.hide(true)
                shHomeCarousel.stopShimmer()
            }
        }
    }

    private fun setupLoadingSubjectClass(loading: Boolean) {
        with(binding) {
            if (loading) {
                rvSubjectClass.hide(true)
                shSubjectClass.show()
                shSubjectClass.startShimmer()
            } else {
                rvSubjectClass.show()
                shSubjectClass.hide(true)
                shSubjectClass.stopShimmer()
            }
        }
    }

    private fun setupLoadingClassUpdate(loading: Boolean) {
        with(binding) {
            if (loading) {
                rvSubjectUpdate.hide(true)
                shSubjectUpdate.show()
                shSubjectUpdate.startShimmer()
            } else {
                rvSubjectUpdate.show()
                shSubjectUpdate.hide(true)
                shSubjectUpdate.stopShimmer()
            }
        }
    }

    private fun setupLoadingSubjectCourse(loading: Boolean) {
        with(binding) {
            if (loading) {
                rvSubjectCourse.hide(true)
                shSubjectCourse.show()
                shSubjectCourse.startShimmer()
            } else {
                rvSubjectCourse.show()
                shSubjectCourse.hide(true)
                shSubjectCourse.stopShimmer()
            }
        }
    }

    private fun setupIsEmptyBanner() {
        with(binding) {
            vpBanner.hide(true)
            dotsBanner.hide(true)
            shHomeCarousel.hide(true)
        }
    }

    private fun setupIsEmptySubjectMapel() {
        // TODOS
    }

    private fun setupIsEmptySubjectClass() {
        // TODOS
    }

    private fun setupError(error: Throwable) {
        ErrorUtils.setupError(this, error,
            showBottomSheet = {
                DibBottomSheet(ErrorType.ERROR_PROCESS_DATA.name, requireContext())
                    .show(childFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                setupViewModel()
            }
        )
    }

    private fun setupRefresh() {
        with(binding) {
            srlHome.setOnRefreshListener {
                if (dataBannerList.isEmpty()) {
                    viewModel.getBanner()
                }
                viewModel.getClass()
                viewModel.getNewUpdate()
                viewModel.getYourAchievement()
                srlHome.isRefreshing = false
            }
        }
    }

    private fun detailCourse(item: DataCourse) {
        if (viewModel.getStatus() != BELUM_DIKONFIRMASI || viewModel.getStatus() != TIDAK_AKTIF) {
            startActivity(Intent(requireContext(), SubjectSummaryActivity::class.java).also {
                val data = DataParams(
                    item.id,
                    item.name,
                    item.tingkat?.name,
                    item.tingkat?.jenjang?.name
                )
                it.putExtra(DATA, data)
            })
        }
    }

    private fun detailModule(idCourse: Int, nameCourse: String, idModule: Int, nameModule: String) {
        if (viewModel.getStatus() != BELUM_DIKONFIRMASI || viewModel.getStatus() != TIDAK_AKTIF) {
            startActivity(
                Intent(
                    requireContext(),
                    DetailLearningModuleActivity::class.java
                ).also {
                    val params = DataParams(
                        idModule,
                        nameModule,
                        EMPTY_STRING,
                        EMPTY_STRING
                    )
                    it.putExtra(DATA, params)
                    it.putExtra(ID, idCourse)
                    it.putExtra(NAME, nameCourse)
                })
        }
    }

    private fun detailVideo(idCourse: Int, nameCourse: String, idModule: Int, nameModule: String) {
        if (viewModel.getStatus() != BELUM_DIKONFIRMASI || viewModel.getStatus() != TIDAK_AKTIF) {
            startActivity(Intent(requireContext(), DetailLearningVideoActivity::class.java).also {
                val params = DataParams(
                    idModule,
                    nameModule,
                    EMPTY_STRING,
                    EMPTY_STRING
                )
                it.putExtra(DATA, params)
                it.putExtra(ID, idCourse)
                it.putExtra(NAME, nameCourse)
            })
        }
    }

    private fun getMapelByClass(item: Tingkat) {
        viewModel.getMapelByClass(item.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}