package com.yoesuv.infomalangbatu.menu.listplace.views

import android.content.res.Configuration
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.data.PlaceLocation
import com.yoesuv.infomalangbatu.databinding.FragmentListplaceBinding
import com.yoesuv.infomalangbatu.menu.listplace.adapters.ListPlaceAdapter
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.FragmentListPlaceViewModel

class FragmentListPlace: Fragment() {

    private lateinit var viewModel: FragmentListPlaceViewModel
    private lateinit var binding: FragmentListplaceBinding
    private lateinit var adapter: ListPlaceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_listplace, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentListPlaceViewModel::class.java)
        binding.listplace = viewModel

        setupRecycler()

        viewModel.setupProperties(context)
        viewModel.getListPlace(PlaceLocation.ALL)
        viewModel.listPlaceResponse.observe(this, Observer {
            onListDataChange(it)
        })
        viewModel.error.observe(this, Observer {
            it?.printStackTrace()
        })
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_list_place, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.listSemua -> viewModel.getListPlace(PlaceLocation.ALL)
            R.id.listKabMalang -> viewModel.getListPlace(PlaceLocation.KAB_MALANG)
            R.id.listKotaBatu -> viewModel.getListPlace(PlaceLocation.KOTA_BATU)
            R.id.listKotaMalang -> viewModel.getListPlace(PlaceLocation.KOTA_MALANG)
        }
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        activity?.recreate()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListPlace(PlaceLocation.ALL)
    }

    private fun setupRecycler(){
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewListPlace.layoutManager = layoutManager
        adapter = ListPlaceAdapter {
            onItemClick(it)
        }
        binding.recyclerViewListPlace.adapter = adapter
    }

    private fun onListDataChange(listPlace: MutableList<PlaceModel>?){
        if(listPlace?.isNotEmpty()!!){
            adapter.submitList(listPlace)
            adapter.notifyDataSetChanged()
        }
    }

    private fun onItemClick(placeModel: PlaceModel) {
        val action = FragmentListPlaceDirections.actionToListPlaceDetail()
        action.dataDetailListPlace = placeModel
        findNavController().navigate(action)
    }

}