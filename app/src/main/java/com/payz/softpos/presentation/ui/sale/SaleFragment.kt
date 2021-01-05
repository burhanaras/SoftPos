package com.payz.softpos.presentation.ui.sale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.payz.softpos.R
import com.payz.softpos.presentation.core.extension.getViewModelFactory
import com.payz.softpos.presentation.core.extension.setSingleClickListener
import com.payz.softpos.presentation.core.view.LoadingPopUp
import kotlinx.android.synthetic.main.fragment_sale.*
import kotlinx.android.synthetic.main.layout_soft_keyboard.*

class SaleFragment : Fragment() {

    private lateinit var saleViewModel: SaleViewModel
    private val loadingPopUp = LoadingPopUp()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sale, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        saleViewModel =
            ViewModelProvider(this, getViewModelFactory()).get(SaleViewModel::class.java)

        saleViewModel.displayedAmount.observe(
            viewLifecycleOwner, { tvSaleAmount.text = it })

        saleViewModel.showProgressPopup.observe(viewLifecycleOwner, {
            loadingPopUp.show(requireContext(), it)
        })
        saleViewModel.updateProgressPopup.observe(viewLifecycleOwner, {
            loadingPopUp.update(it)
        })

        btnKeyboardOne.setOnClickListener { saleViewModel.onKeyPress(1) }
        btnKeyboardTwo.setOnClickListener { saleViewModel.onKeyPress(2) }
        btnKeyboardThree.setOnClickListener { saleViewModel.onKeyPress(3) }
        btnKeyboardFour.setOnClickListener { saleViewModel.onKeyPress(4) }
        btnKeyboardFive.setOnClickListener { saleViewModel.onKeyPress(5) }
        btnKeyboardSix.setOnClickListener { saleViewModel.onKeyPress(6) }
        btnKeyboardSeven.setOnClickListener { saleViewModel.onKeyPress(7) }
        btnKeyboardEight.setOnClickListener { saleViewModel.onKeyPress(8) }
        btnKeyboardNine.setOnClickListener { saleViewModel.onKeyPress(9) }
        btnKeyboardZero.setOnClickListener { saleViewModel.onKeyPress(0) }
        btnKeyboardComma.setOnClickListener { saleViewModel.onKeyPressComma() }
        btnKeyboardBackSpace.setOnClickListener { saleViewModel.onKeyPressBackSpace() }
        btnSaleNext.setSingleClickListener { saleViewModel.onClickNext() }
    }

}