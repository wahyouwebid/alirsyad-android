package com.alirsyad.app.ui.register

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirsyad.app.databinding.FragmentBottomSheetCommonBinding
import com.alirsyad.app.ui.adapter.JenjangAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class JenjangBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupData()
        setupBehaviour()
        setupListener()
    }

    private fun setupAdapter(){
        with(binding){
            rvData.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = jenjangAdapter
            }
        }
    }

    private fun setupData(){
        viewModel.jenjangList.observe(viewLifecycleOwner){
            jenjangAdapter.setData(it)
        }
    }

    private fun setupBehaviour(){
        dialog.behavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //Not Implemented
            }

        })
    }

    private fun selectData(item: com.alirsyad.app.data.model.jenjang.Jenjang) {
        viewModel.jenjangSelected.postValue(item)
        dismiss()
    }

    private fun setupListener(){
        with(binding){
            ivClose.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private val binding : FragmentBottomSheetCommonBinding by lazy {
        FragmentBottomSheetCommonBinding.inflate(layoutInflater)
    }

    private val jenjangAdapter : JenjangAdapter by lazy {
        JenjangAdapter { item -> selectData(item)}
    }

    private val viewModel: RegisterViewModel by activityViewModels()

    private lateinit var dialog: BottomSheetDialog

}