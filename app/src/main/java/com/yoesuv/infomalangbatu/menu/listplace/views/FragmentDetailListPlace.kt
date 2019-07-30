package com.yoesuv.infomalangbatu.menu.listplace.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentDetailListplaceBinding
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.FragmentDetailListPlaceViewModel

class FragmentDetailListPlace: Fragment() {

    private lateinit var binding: FragmentDetailListplaceBinding
    private lateinit var viewModel: FragmentDetailListPlaceViewModel

    private val args: FragmentDetailListPlaceArgs by navArgs()
    private var placeModel: PlaceModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        placeModel = args.dataDetailListPlace
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_listplace, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = FragmentDetailListPlaceViewModel(activity!!.application, placeModel)
        binding.detailListPlace = viewModel
    }

}