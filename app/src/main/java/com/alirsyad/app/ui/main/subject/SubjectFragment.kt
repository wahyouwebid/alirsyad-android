package com.alirsyad.app.ui.main.subject

import android.content.Intent
import android.os.Bundle
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
import com.alirsyad.app.data.model.courses.DataCourse
import com.alirsyad.app.data.model.courses.Tingkat
import com.alirsyad.app.data.state.CourseState
import com.alirsyad.app.data.state.TingkatState
import com.alirsyad.app.databinding.FragmentSubjectBinding
import com.alirsyad.app.ui.MainViewModel
import com.alirsyad.app.ui.adapter.SubjectClassAdapter
import com.alirsyad.app.ui.adapter.SubjectClassTKAdapter
import com.alirsyad.app.ui.adapter.SubjectCourseAdapter
import com.alirsyad.app.ui.subjectsummary.SubjectSummaryActivity
import com.alirsyad.app.utils.Constant.Intent.DATA
import com.alirsyad.app.utils.Constant.VisitorStatus.BELUM_DIKONFIRMASI
import com.alirsyad.app.utils.Constant.VisitorStatus.TIDAK_AKTIF
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils.setupError
import com.alirsyad.app.utils.Utils
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent
import com.jakewharton.rxbinding3.widget.textChangeEvents
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.Locale
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SubjectFragment : Fragment() {

    private val binding: FragmentSubjectBinding by lazy {
        FragmentSubjectBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()
    private var dataSubjectCourse : List<DataCourse> = ArrayList()
    private var subjectClassId = 0

    private val subjectClassAdapter: SubjectClassAdapter by lazy {
        SubjectClassAdapter(viewModel) { item -> getCourseByClass(item) }
    }

    private val subjectClassTKAdapter: SubjectClassTKAdapter by lazy {
        SubjectClassTKAdapter(viewModel) { item -> getCourseByClass(item) }
    }

    private val subjectCourseAdapter: SubjectCourseAdapter by lazy {
        SubjectCourseAdapter(
            showMore = {},
            showDetail = { item -> detailCourse(item) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupViewModel()
        setupObserveViewModel()
        setData()
        setupSearch()
    }

    private fun setData() {
        with(binding) {
            toolbar.setToolbar(getString(R.string.title_subject))
        }
    }

    private fun setupAdapter() {
        with(binding) {
            rvSubjectClass.also {
                it.adapter = getAdapter()
                it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            rvSubjectCourse.also {
                it.isNestedScrollingEnabled = false
                it.adapter = subjectCourseAdapter
                it.layoutManager = GridLayoutManager(context, 4)
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

    private fun getCourseByClass(item: Tingkat) = with(binding) {
        etSearch.text.clear()
        subjectClassId = item.id
        viewModel.getSubjectCourse(item.id)
    }

    private fun setupViewModel() {
        viewModel.getSubjectClass()
    }

    private fun setupObserveViewModel() {
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
                    setupErrorSubjectClass(it.error)
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
                    setupErrorSubjectCourse(it.error)
                }
            }
        }
    }

    private fun setupSearch(){
        with(binding){
            etSearch.textChangeEvents()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TextViewTextChangeEvent>() {
                    override fun onNext(t: TextViewTextChangeEvent) {
                        val keyword = t.text.toString()
                        if(keyword.trim{it <= ' '}.isNotEmpty() && keyword.trim{it <= ' '}.length >= 3) {
                            filterDataSubjectClass(keyword)
                        }else{
                            viewModel.getSubjectCourse(subjectClassId)
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }
    }

    fun filterDataSubjectClass(text: String?) {
        val temp: MutableList<DataCourse> = ArrayList()
        for (data in dataSubjectCourse) {
            if (data.name.lowercase(Locale.ROOT).contains(text!!.lowercase(Locale.getDefault()))) {
                temp.clear()
                temp.add(data)
            }
        }
        subjectCourseAdapter.setData(temp)
    }

    private fun setupLoadingSubjectClass(loading: Boolean) {
        with(binding) {
            if (loading) {
                rvSubjectClass.hide()
                shSubjectsClass.show()
                shSubjectsClass.startShimmer()
            } else {
                rvSubjectClass.show()
                shSubjectsClass.hide(true)
                shSubjectsClass.stopShimmer()
            }
        }
    }

    private fun setupLoadingSubjectCourse(loading: Boolean) {
        with(binding) {
            if (loading) {
                rvSubjectCourse.hide()
                shSubjectCourse.show()
                shSubjectCourse.startShimmer()
            } else {
                rvSubjectCourse.show()
                shSubjectCourse.hide(true)
                shSubjectCourse.stopShimmer()
            }
        }
    }

    private fun setDataSubjectClass(data: List<Tingkat>) {
        if (data.isNotEmpty()) {
            if (Utils.isNumeric(viewModel.getTingkat()) == true) {
                subjectClassAdapter.setData(data)
            } else {
                subjectClassTKAdapter.setData(data)
            }
            if (viewModel.getIsTab()) {
                if (viewModel.getClassPosition() == 0) {
                    viewModel.getMapelByClass(data[0].id)
                } else {
                    binding.rvSubjectClass.scrollToPosition(viewModel.getClassId() - 1)
                    viewModel.getMapelByClass(viewModel.getClassId())
                }
            } else {
                setSubjectByClass(data)
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

    private fun setupErrorSubjectClass(error: Throwable) {
        setupError(this, error,
            showBottomSheet = {
                DibBottomSheet(ErrorType.ERROR_PROCESS_DATA.name, requireContext())
                    .show(childFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                setupViewModel()
            }
        )
    }

    private fun setDataSubjectCourse(data: List<DataCourse>){
        when {
            data.isNotEmpty() -> {
                dataSubjectCourse = data
                subjectCourseAdapter.setData(data)
            }
        }
    }

    private fun setupErrorSubjectCourse(error: Throwable) {
        setupLoadingSubjectCourse(false)
        setupError(this, error,
            showBottomSheet = {
                DibBottomSheet(ErrorType.ERROR_PROCESS_DATA.name, requireContext())
                    .show(childFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                setupViewModel()
            }
        )
    }

    private fun detailCourse(item: DataCourse) {
        if (viewModel.getStatus() != BELUM_DIKONFIRMASI || viewModel.getStatus() != TIDAK_AKTIF) {
            startActivity(Intent(requireContext(), SubjectSummaryActivity::class.java).also {
                val data = com.alirsyad.app.data.model.params.DataParams(
                    item.id,
                    item.name,
                    item.tingkat?.name,
                    item.tingkat?.jenjang?.name
                )
                it.putExtra(DATA, data)
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}