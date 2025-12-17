package com.alirsyad.app.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.model.simulation.MataPelajaran
import com.alirsyad.app.data.model.simulation.Simulasi
import com.alirsyad.app.databinding.AdapterSimulationBinding
import com.alirsyad.app.ui.learning.learningsimulation.detail.DetailLearningSimulationActivity

class SimulationAdapter(
    private val showDetail: (MataPelajaran) -> Unit,
    private val fragment: Fragment
) : RecyclerView.Adapter<SimulationAdapter.ViewHolder>() {

    private var data = ArrayList<MataPelajaran>()
    private lateinit var context : Context

    fun setData(itemList: List<MataPelajaran>) {
        context = fragment.requireContext()
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            tvTitleSimulation.text = item.name
            tvProgress.text = "${item.detail.progress.done} dari ${item.detail.progress.total} simulasi selesai"
            pbProgress.progress = item.detail.progress.percentage
            root.setOnClickListener {
                showDetail(data[position])
            }
            onBindSimulationContentAdapter(holder, item)
        }
    }

    private fun onBindSimulationContentAdapter(holder: ViewHolder, item : MataPelajaran){
        with(holder.view) {
            val adapter: SimulationContentAdapter by lazy {
                SimulationContentAdapter {simulasi -> goToDetail(simulasi)}
            }
            rvSimulationContent.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(context)
            }
            adapter.setData(item.detail.simulasis)
        }
    }

    private fun goToDetail(simulasi: Simulasi) {
        context.startActivity(Intent(context, DetailLearningSimulationActivity::class.java).also {
            val data = DataParams(
                simulasi.id,
                simulasi.simulasiUrl,
                "",
                ""
            )
            it.putExtra("data", data)
        })
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterSimulationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterSimulationBinding) : RecyclerView.ViewHolder(view.root)

}