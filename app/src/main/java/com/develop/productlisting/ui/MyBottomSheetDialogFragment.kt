package com.develop.productlisting.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.develop.productlisting.R
import com.develop.productlisting.databinding.FilterMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var listener: OnSortOptionSelectedListener? = null

    interface OnSortOptionSelectedListener {
        fun onSortOptionSelected(filterType: FilterType)
    }


    private lateinit var bind : FilterMenuBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnSortOptionSelectedListener){
            listener = context
        }else{
            throw ClassCastException("$context must implement OnSortOptionSelectedListener")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FilterMenuBinding.inflate(inflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var filterValue : FilterType = FilterType.NONE
        bind.filterBtnGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.highToLow -> {
                    filterValue = FilterType.HIGH_TO_LOW
                }
                R.id.lowToHigh -> {
                    filterValue = FilterType.LOW_TO_HIGH
                }
                R.id.ascendByName -> {
                    filterValue = FilterType.ASCEND_BY_NAME
                }
                R.id.descendByName -> {
                    filterValue = FilterType.DESCEND_BY_NAME
                }else -> {
                filterValue = FilterType.NONE
                }
            }
        }

        bind.btnApply.setOnClickListener {
            if(filterValue == FilterType.NONE){
                Toast.makeText(this.context?.applicationContext, "Please select filter type", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            listener?.onSortOptionSelected(filterValue)
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }
}