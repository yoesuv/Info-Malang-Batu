package com.yoesuv.infomalangbatu.menu.listplace.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.FragmentDetailListplaceBinding
import com.yoesuv.infomalangbatu.main.views.MainActivity
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.FragmentDetailListPlaceViewModel
import com.yoesuv.infomalangbatu.utils.bindings.ViewModelFragmentFactory

class FragmentDetailListPlace: Fragment() {

    private lateinit var binding: FragmentDetailListplaceBinding
    private val viewModel: FragmentDetailListPlaceViewModel by viewModels { ViewModelFragmentFactory(placeModel as Any) }

    private val args: FragmentDetailListPlaceArgs by navArgs()
    private var placeModel: PlaceModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        placeModel = args.dataDetailListPlace
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_listplace, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideNavigation(true)
        binding.detailListPlace = viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).hideNavigation(false)
    }

}