package com.alirsyad.app.component.customview

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.alirsyad.app.R
import com.alirsyad.app.utils.Constant.ValueChart.HIGH_VALUE
import com.alirsyad.app.utils.Constant.ValueChart.LOW_VALUE
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.math.RoundingMode
import java.text.DecimalFormat


@SuppressLint("ViewConstructor")
class ChartMakerView(context: Context?, private val barData: BarData) :
    MarkerView(context, R.layout.layout_tooltips_chart) {
    private val tvContent: TextView = findViewById(R.id.tv_tooltips)
    private val format = DecimalFormat("#")
    override fun refreshContent(e: Entry, highlight: Highlight?) {
        format.roundingMode = RoundingMode.CEILING
        val data = barData.dataSets
        val index = highlight?.dataSetIndex
        if (e.y == HIGH_VALUE || e.y == LOW_VALUE) {
            tvContent.text = String.format("${data[index!!].label}: %s", format.format(0f))
        } else {
            tvContent.text = String.format("${data[index!!].label}: %s", format.format(e.y))
        }

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}