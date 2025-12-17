package com.alirsyad.app.ui.main.ereport

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.ChartMakerView
import com.alirsyad.app.component.customview.DibBarChartRender
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.ereport.EReportChart
import com.alirsyad.app.data.model.ereport.EReportCurrentScore
import com.alirsyad.app.data.state.EReportChartState
import com.alirsyad.app.data.state.EReportCurrentScoreState
import com.alirsyad.app.databinding.FragmentEReportBinding
import com.alirsyad.app.ui.MainViewModel
import com.alirsyad.app.ui.ereportscore.EReportScoreActivity
import com.alirsyad.app.utils.Constant.Intent.DATA
import com.alirsyad.app.utils.Constant.ValueChart.HIGH_VALUE
import com.alirsyad.app.utils.Constant.ValueChart.LOW_VALUE
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.Utils.drawXAxisValue
import com.github.mikephil.charting.utils.ViewPortHandler
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode
import java.text.DecimalFormat


@AndroidEntryPoint
class EReportFragment : Fragment(), OnChartValueSelectedListener {

    private val binding: FragmentEReportBinding by lazy {
        FragmentEReportBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    private val adapter: EReportAdapter by lazy {
        EReportAdapter{ item -> goToScore(item) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupAdapter()
        setupToolbar()
        setupObserveViewModel()
        setupListener()
        setupBarDataSet()
    }

    private fun setupToolbar() {
        with(binding) {
            toolbar.setToolbar(getString(R.string.title_e_report))
        }
    }

    private fun setupViewModel() {
        viewModel.getEReportChart()
        viewModel.getEReportCurrentScore()
    }

    private fun setupObserveViewModel() {
        viewModel.eReportChart.observe(viewLifecycleOwner) {
            when(it) {
                is EReportChartState.Loading -> setLoadingChart(true)
                is EReportChartState.Result -> setDataChart(it.data.data)
                is EReportChartState.Error -> setupError(it.error)
            }
        }

        viewModel.eReportCurrentScore.observe(viewLifecycleOwner) {
            when(it) {
                is EReportCurrentScoreState.Loading -> setLoadingScore(true)
                is EReportCurrentScoreState.Result -> setDataScore(it.data.data)
                is EReportCurrentScoreState.Error -> setupError(it.error)
            }
        }
    }

    private fun setupBarDataSet() = with(binding) {
        chart.setNoDataText("")
        chart.setPinchZoom(true)
        chart.setDrawBarShadow(false)
        chart.setDrawGridBackground(false)
        chart.axisLeft.setDrawGridLines(false)
        chart.setFitBars(true)
        chart.invalidate()
        chart.setPinchZoom(false)
        chart.enableScroll()
        chart.setTouchEnabled(true)
        chart.isClickable = true
        chart.legend.isEnabled = false
        chart.isDoubleTapToZoomEnabled = false
        chart.description.isEnabled = false
        chart.animateY(1500)
        chart.renderer = DibBarChartRender(
            chart,
            chart.animator,
            chart.viewPortHandler,
            25F
        )
    }


    private fun setDataChart(data: List<EReportChart>) = with(binding){
        setLoadingChart(false)
        if (data.size > 1) {
            chart.setScaleMinima(3.5f, 1f)
        }
        val typeface = this@EReportFragment.context?.let {
            ResourcesCompat.getFont(
                it,
                R.font.poppins_semi_bold
            )
        }
        val valuesColor = "#0ABC5D"
        val values2Color = "#FFAB05"
        val values3Color = "#C52421"
        val barSpace = 0f
        val groupSpace = 0.52f
        val barWidth = 0.16f
        val groupCount = data.size
        val xAxis = chart.xAxis

        xAxis.position = XAxisPosition.BOTTOM
        xAxis.typeface = typeface
        xAxis.textSize = 12f
        xAxis.isEnabled = true
        xAxis.labelCount = data.size
        xAxis.setCenterAxisLabels(true)
        chart.xAxis.setDrawGridLines(false)
        chart.axisLeft.setDrawGridLines(true)
        chart.axisRight.setDrawGridLines(true)
        chart.axisRight.isEnabled = false
        chart.xAxis.isGranularityEnabled = true
        chart.axisRight.axisMinimum = 0f
        chart.axisLeft.axisMinimum = 0f
        chart.xAxis.yOffset =  20f


        val values = ArrayList<BarEntry>()
        val values2 = ArrayList<BarEntry>()
        val values3 = ArrayList<BarEntry>()
        val labels = arrayOfNulls<String>(data.size)
        data.forEachIndexed { index, level ->
            labels[index] = level.mataPelajaran
            values.add(
                BarEntry(
                    index.toFloat(),
                    if (level.mudah?.percentage!! == 0f) {
                        checkDataChart(data)
                    } else {
                        level.mudah.percentage
                    }
                )
            )
            
            values2.add(
                BarEntry(
                    index.toFloat(),
                    if (level.sedang?.percentage!! == 0f) {
                        checkDataChart(data)
                    } else {
                        level.sedang.percentage
                    }
                )
            )
            
            values3.add(
                BarEntry(
                    index.toFloat(),
                    if (level.sulit?.percentage!! == 0f) {
                        checkDataChart(data)
                    } else {
                        level.sulit.percentage
                    }
                )
            )
        }

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        chart.setXAxisRenderer(
            CustomXAxisRenderers(
                chart.viewPortHandler,
                chart.xAxis,
                chart.getTransformer(YAxis.AxisDependency.LEFT)
            )
        )

        val barDataSet1 = BarDataSet(values, getString(R.string.title_easy))
        barDataSet1.color = Color.parseColor(valuesColor)
        val barDataSet2 = BarDataSet(values2, getString(R.string.title_medium))
        barDataSet2.color = Color.parseColor(values2Color)
        val barDataSet3 = BarDataSet(values3, getString(R.string.title_hard))
        barDataSet3.color = Color.parseColor(values3Color)

        val barData = BarData(barDataSet1, barDataSet2, barDataSet3)
        barData.setValueTextSize(10f)
        barData.setDrawValues(true)
        barData.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val df = DecimalFormat("#")
                df.roundingMode = RoundingMode.CEILING
                return if (value == HIGH_VALUE || value == LOW_VALUE) {
                    df.format(0f)
                } else {
                    df.format(value)
                }
            }
        })
        chart.data = barData

        val marker: IMarker = ChartMakerView(requireContext(), barData)
        chart.marker = marker

        xAxis.axisMinimum = 1f
        xAxis.granularity = 1F
        xAxis.setCenterAxisLabels(true)
        xAxis.axisMaximum = data.size.toFloat()

        chart.barData.barWidth = barWidth
        chart.xAxis.axisMinimum = 0f
        chart.extraBottomOffset = 20f
        chart.groupBars(0f, groupSpace, barSpace)
        chart.xAxis.axisMaximum = chart.barData.getGroupWidth(groupSpace, barSpace) * groupCount

        chart.invalidate()
    }

    private fun checkDataChart(data: List<EReportChart>) : Float{

        val dataPercent = data.filter {
            it.mudah?.percentage!! > 0f ||
            it.sedang?.percentage!! > 0f ||
            it.sulit?.percentage!! > 0f
        }

        return if (dataPercent.isNotEmpty()) HIGH_VALUE else LOW_VALUE
    }

    internal class CustomXAxisRenderers(
        viewPortHandler: ViewPortHandler?,
        xAxis: XAxis?,
        trans: Transformer?,
    ) : XAxisRenderer(viewPortHandler, xAxis, trans) {
        override fun drawLabel(
            c: Canvas?,
            formattedLabel: String,
            x: Float,
            y: Float,
            anchor: MPPointF?,
            angleDegrees: Float,
        ) {
            val line: List<String> = formattedLabel.split("\n")
            drawXAxisValue(c,
                line[0],
                x,
                y,
                mAxisLabelPaint,
                anchor,
                angleDegrees)
            for (i in 1 until line.size) {
                drawXAxisValue(c,
                    line[i],
                    x,
                    y + mAxisLabelPaint.textSize * i,
                    mAxisLabelPaint,
                    anchor,
                    angleDegrees)
            }
        }
    }
    
    private fun setLoadingChart(isLoading: Boolean) = with(binding){
        if (isLoading) {
            shChart.show()
        } else {
            shChart.hide(true)
        }
    }

    private fun setDataScore(data: List<EReportCurrentScore>) = with(binding) {
        setLoadingScore(false)
        if (data.isNotEmpty()) {
           adapter.setData(data)
            dibEmpty.hide(true)
            tvSeeAll.show()
        } else {
            dibEmpty.show()
            dibEmpty.setData(
                getString(R.string.title_e_report_empty_state),
                getString(R.string.title_e_report_empty_state_description)
            )
            tvSeeAll.hide()
        }
    }

    private fun setLoadingScore(isLoading: Boolean) = with(binding){
        if (isLoading) {
            shScore.show()
        } else {
            shScore.hide(true)
        }
    }

    private fun setupAdapter() {
        with(binding) {
            rvYourScore.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun setupListener() = with(binding){
        tvSeeAll.setOnClickListener {
            startActivity(Intent(requireContext(), EReportScoreActivity::class.java))
        }
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

    private fun goToScore(data: EReportCurrentScore) {
        startActivity(
            Intent(requireContext(), EReportScoreActivity::class.java).also {
                it.putExtra(DATA, data)
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

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.d("Entry selected", e.toString())
        Log.d("", "low: " + binding.chart.lowestVisibleX + ", high: "    + binding.chart.highestVisibleX)
    }

    override fun onNothingSelected() {}
}