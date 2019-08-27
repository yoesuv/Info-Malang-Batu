package com.yoesuv.infomalangbatu.menu.listplace.views

import android.content.res.Configuration
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.*
import androidx.navigation.fragment.findNavController
import com.yoesuv.infomalangbatu.R
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
        viewModel = ViewModelProviders.of(this).get(FragmentListPlaceViewModel::class.java)
        binding.listplace = viewModel

        setupRecycler()
        setupSwipeRefresh()

        viewModel.getListPlace()
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
            R.id.listSemua -> viewModel.getListPlace()
            R.id.listKabMalang -> viewModel.getListPlaceKabMalang()
            R.id.listKotaBatu -> viewModel.getListPlaceKotaBatu()
            R.id.listKotaMalang -> viewModel.getListPlaceKotaMalang()
        }
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        activity?.recreate()
    }

    private fun setupRecycler(){
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewListPlace.layoutManager = layoutManager
        adapter = ListPlaceAdapter {
            onItemClick(it)
        }
        binding.recyclerViewListPlace.adapter = adapter
    }

    private fun setupSwipeRefresh(){
        binding.swipeRefreshLayoutListPlace.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        binding.swipeRefreshLayoutListPlace.setOnRefreshListener {
            binding.swipeRefreshLayoutListPlace.isRefreshing = false
            activity?.invalidateOptionsMenu()
            viewModel.getListPlace()
        }
    }

    private fun onListDataChange(listPlace: MutableList<PlaceModel>?){
        if(listPlace?.isNotEmpty()!!){
            adapter.submitList(listPlace)
        }
    }

    private fun onItemClick(placeModel: PlaceModel) {
        val action = FragmentListPlaceDirections.actionToListPlaceDetail()
        action.dataDetailListPlace = placeModel
        findNavController().navigate(action)
    }

}